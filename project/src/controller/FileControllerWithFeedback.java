package controller;

import controller.Tasks.BackgroundTask;
import controller.Threads.ProgressThread;
import logger.Logger;
import models.*;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FileControllerWithFeedback {

    public static IndexFile[] getFilesWithSpecificString(String path, String matchString, ProgressThread pt) {

        IndexFile[] iF;
        File[] indexFiles;
        File[] files = new File(path).listFiles();

        if (files != null) {

            pt.startWithCounting();
            pt.setMaxIterations(2);
            pt.setMaxCounterAndIteration(files.length);

            indexFiles = new File[files.length];

            for (int i=0; i < files.length; i++) {
                if (files[i].getName().contains(matchString)) {
                    indexFiles[i] = files[i];
                }

                pt.increaseCurrentCounter();
            }

            indexFiles = Arrays.stream(indexFiles)
                    .filter(s -> (s != null && s.length() > 0))
                    .toArray(File[]::new);

            pt.setMaxCounterAndIteration(indexFiles.length);

            iF = new IndexFile[indexFiles.length];

            for(int i=0; i< indexFiles.length; i++){
                iF[i] = new IndexFile(indexFiles[i]);
                pt.increaseCurrentCounter();
            }
            pt.finish();
        }
        else{
            iF = new IndexFile[0];
        }
        return iF;
    }

    public static StorageElementList getAllElements(String path, ProgressThread pt) {
        pt.startWithCounting();
        StorageElementList a = FileControllerWithFeedback.getAllElements(path, path, 1, pt).sortAndGet();
        pt.finish();
        return a;
    }

    public static StorageElementList getAllElements(String path, String rootDir, int index, ProgressThread pt) {

        long lastModified = 0;
        List<StorageElement> returnArray = new ArrayList<>();
        File element = new File(path);

        if (element.isDirectory()) {

            StorageElement storageElementDir = new StorageElement(element, rootDir);
            storageElementDir.setLft(index);
            index++;

            File[] containedElements = element.listFiles();

            try {

                if (containedElements != null) {

                    StorageElement se;
                    rootDir = new File(rootDir).getAbsolutePath();

                    for (File containedElement : containedElements) {

                        se = new StorageElement(containedElement.getAbsolutePath(), rootDir);

                        if (se.isRegularFile()) {
                            se.setLft(index);
                            index++;
                            se.setRgt(index);
                            index++;
                            pt.count();
                            returnArray.add(se);
                            if (lastModified < se.getLastModified()) {
                                lastModified = se.getLastModified();
                            }
                        }

                        if (se.isDirectory()) {
                            StorageElementList sel = FileControllerWithFeedback.getAllElements(se.getAbsolutePath(), rootDir, index, pt);
                            index = sel.getLatestLft();
                            if (lastModified < sel.getLastModified()) {
                                lastModified = sel.getLastModified();
                            }
                            returnArray.addAll(sel.getElements());
                        }
                    }
                }

                storageElementDir.setRgt(index);
                index++;
                if (lastModified != 0) {
                    storageElementDir.setChangedAtSpecific(lastModified);
                }
                returnArray.add(storageElementDir);


            } catch (Exception e) {
                Logger.printErr("keine Elemente vorhanden: " + e.getMessage());
            }
        }

        return new StorageElementList(returnArray, rootDir, index, lastModified);
    }

    public static void compareJSONFiles(String sourcePath, String targetPath, String compareFilePath, boolean isHardSync, boolean slowMode, ProgressThread pt) {

        // TODO: create two ways, a safe way with renaming detection, and another way without this
        // the other way is faster, because there will skip folders with a earlier modified date
        // the problem appeared during nesting sets
        //        TODO: Testing --> !!!

        // Was ist mit Dateien die auf B nochmal verändert wurden? Werden diese überschrieben? --> logisch müssten sie es
        // --> sie werden ignoriert -->
        // TODO: new List for files, which are on the target dir newer as in the source dir


        // TODO: Testing: What is with files without read/write access?
        // TODO: What is with files with same change and creation Date?

        List<StorageElement> comparedList = new ArrayList<>();
        List<StorageElement> deleteList = new ArrayList<>();
        List<StorageElement> newTargetList = new ArrayList<>();

        StorageElementList sourceElementsList = new StorageElementList().readJSON(sourcePath);
        StorageElementList targetElementsList = new StorageElementList().readJSON(targetPath);

        ProcessingElementList compareFile = new ProcessingElementList();
        compareFile.sourceDirPath = sourceElementsList.getDirPath();
        compareFile.targetDirPath = targetElementsList.getDirPath();

        StorageElement[] sourceElements = sourceElementsList.getElementsArray();
        StorageElement[] targetElements = targetElementsList.getElementsArray();
        targetElements[0] = null;

        sourceElementsList = null;
        targetElementsList = null;

        getNotContainedElements(sourceElements, targetElements, false, slowMode, comparedList, deleteList, pt);

        if (isHardSync) {
            getNotContainedElements(targetElements, sourceElements, true, slowMode, comparedList, deleteList, pt);
        }

        compareFile.setCopyList(comparedList);
        compareFile.setDeleteList(deleteList);
        compareFile.setNewTargetList(newTargetList);
        compareFile.saveAsJSON(compareFilePath);
    }


    private static void getNotContainedElements(StorageElement[] firstElements, StorageElement[] secondElements, boolean collectElementsToDelete, boolean slowMode, List<StorageElement> comparedList, List<StorageElement> deleteList, ProgressThread pt) {

        //TODO: einzelne datei in vielen neuen unterordnern --> legt jeden ordner zum Kopieren an

        int minRgt = 1; // because the first source Element should be ignored
        int indexInTarget;

        StorageElement element2, tmpElement2;
        boolean changedAtFound, nameFound, isDifferent, sameName, laterChangeDate, sameChangeDate, earlierChangeDate, sameSize, sameChildrenCount, isDirAndDiffChilds, hasChildren, renamed;

        List<StorageElement> returnList = new ArrayList<>();


        for (StorageElement element1 : firstElements) {
            if (element1 != null) {
                if (element1.getLft() > minRgt) {
                    indexInTarget = 0;
                    element2 = new StorageElement();

                    changedAtFound = false;
                    nameFound = false;

                    // find the source File in the target

                    // Try 1
                    // contains target the sourceElement with the specific property 'changed_at'
                    if (slowMode || collectElementsToDelete) {
                        while (indexInTarget < secondElements.length) {
                            tmpElement2 = secondElements[indexInTarget];
                            if (tmpElement2 != null && tmpElement2.getLft() > 1) {
                                if (tmpElement2.getLastModified() == element1.getLastModified() && tmpElement2.isDir() == element1.isDir()) {
                                    element2 = tmpElement2;
                                    changedAtFound = true;
                                    break;
                                }
                            }
                            indexInTarget++;
                        }
                    }
                    // Try 2
                    // if not
                    // contains target the sourceElement with the specific property 'relative_path' (name)
                    if (!changedAtFound) {
                        indexInTarget = 0;
                        while (indexInTarget < secondElements.length) {
                            tmpElement2 = secondElements[indexInTarget];
                            if (tmpElement2 != null && tmpElement2.getLft() > 1) {
                                if (tmpElement2.getRelativePath().equals(element1.getRelativePath())) {
                                    element2 = tmpElement2;
                                    nameFound = true;
                                    break;
                                }
                            }
                            indexInTarget++;
                        }
                    }

                    // if an element is found with path or changed_at
                    // then check if name, size or changed_at is different
                    if (changedAtFound || nameFound) {

                        element2.setIsCompared(true);
                        element1.setIsCompared(true);

                        isDifferent = false;

                        if (!nameFound) {
                            sameName = element1.getRelativePath().equals(element2.getRelativePath());
                            if (!sameName) {
                                isDifferent = true;
                                element1.setDifferentName(true);
                            }
                        }

                        if (!changedAtFound) {
                            laterChangeDate = element1.getLastModified() > element2.getLastModified();
                            if (laterChangeDate) {
                                isDifferent = true;
                                element1.setDifferentChanged(true);
                            }
                            sameChangeDate = element1.getLastModified() == element2.getLastModified();
                            if (sameChangeDate) {
                                // ???
                            }
                            earlierChangeDate = element1.getLastModified() < element2.getLastModified();
                            if (earlierChangeDate) {
                                // newTargetList.add(source);
                                isDifferent = true;
                                element1.setDifferentChanged(true);
                            }
                        }

                        sameSize = element1.getFileSize() == element2.getFileSize();
                        if (!sameSize) {
                            isDifferent = true;
                            element1.setDifferentSize(true);
                        }

                        sameChildrenCount = element1.getChildrenCount() == element2.getChildrenCount();
                        if (!sameChildrenCount) {
                            isDifferent = true;
                            element1.setDifferentChildrenCount(true);
                        }

                        // In cases where there is a new file in B,
                        // the different number of children should not result in copying a folder.
                        isDirAndDiffChilds = element1.isDifferentChildrenCount() && element1.isDir();

                        // Dirs are only copy, if these are empty
                        hasChildren = element1.getChildrenCount() > 0;
                        if (isDifferent) {
                            if (!collectElementsToDelete) {
                                if (!(hasChildren)) {
                                    returnList.add(element1);
                                }
                            } else {
                                if (!hasChildren) {
                                    boolean found = false;
                                    for (StorageElement aComparedList : comparedList) {
                                        if (aComparedList.getRelativePath().equals(element2.getRelativePath())) {
                                            found = true;
                                            break;
                                        }
                                    }
                                    if (!found) {
                                        comparedList.add(element2);
                                    }
                                }
                            }
                        } else {
                            if (!slowMode && !collectElementsToDelete) {
                                // file will be ignored
                                // if dir, all files in them will be ignored
                                minRgt = element1.getRgt();
                            }
                        }

                        if (!nameFound && element1.isDifferentName() && collectElementsToDelete && !hasChildren) {
                            boolean found = false;      // if the element found in the source tree, then this don't has to delete, because it is still there
                            indexInTarget = 0;
                            while (indexInTarget < secondElements.length) { //also the really source tree
                                tmpElement2 = secondElements[indexInTarget];
                                if (tmpElement2 != null && tmpElement2.getLft() > 1) {
                                    if (tmpElement2.getRelativePath().equals(element1.getRelativePath())) {
                                        found = true;
                                        break;
                                    }
                                }
                                indexInTarget++;
                            }
                            if (!found) {
                                deleteList.add(element1);
                            }
                        }
                    } else {
                        element1.setIsNewFile(true);
                        returnList.add(element1);
                    }
                }
            }
        }
        if (collectElementsToDelete) {
            deleteList.addAll(returnList);
        } else {
            comparedList.addAll((returnList));
        }
    }

    public static void sync(String compareFilePath, ProgressThread pt) throws Exception {
        //TODO: implement background tasks to get a progress
        //@QuentinWeber assigned

        ProcessingElementList comparedElements = (ProcessingElementList) new ProcessingElementList().readJSON(compareFilePath);

        minimizedStorageElement[] copyingElements = comparedElements.getCopyList();
        minimizedStorageElement[] deletingElements = comparedElements.getDeleteList();

        String sourcePath = comparedElements.sourceDirPath;
        String targetPath = comparedElements.targetDirPath;
        String filePath = "";

        File targetDir;
        boolean canCopy;

        for (minimizedStorageElement copyingElement : copyingElements) {
            filePath = copyingElement.getRelativePath();

            // get target Dir and create all dirs to this
            targetDir = new File((new File(targetPath + filePath)).getParentFile().getAbsolutePath());
            canCopy = false;
            if (!targetDir.exists()) {
                if (targetDir.mkdirs()) {
                    canCopy = true;
                }
            } else {
                canCopy = true;
            }
            if (canCopy) {
                Files.copy(new File(sourcePath + filePath).toPath(), new File(targetPath + filePath).toPath(), REPLACE_EXISTING, COPY_ATTRIBUTES);
            }
        }

        for (minimizedStorageElement deletingElement : deletingElements) {
            filePath = deletingElement.getAbsolutePath();
            Files.deleteIfExists(new File(filePath).toPath());
        }
    }
}
