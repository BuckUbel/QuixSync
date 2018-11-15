package controller;

import logger.Logger;
import models.*;

import java.io.File;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileController {

    public static IndexFile[] getFilesWithSpecificString(String path, String matchString) {

        IndexFile[] iF;
        File[] indexFiles = new File[0];
        File[] files = new File(path).listFiles();


        if (files != null) {

            indexFiles = new File[files.length];

            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().contains(matchString)) {
                    indexFiles[i] = files[i];
                }
            }
            indexFiles = Arrays.stream(indexFiles)
                    .filter(s -> (s != null && s.length() > 0))
                    .toArray(File[]::new);

            iF = new IndexFile[indexFiles.length];

            for (int i = 0; i < indexFiles.length; i++) {
                iF[i] = new IndexFile(indexFiles[i]);
            }
        } else {
            iF = new IndexFile[0];
        }
        return iF;
    }

    public static StorageElementList getAllElements(String path) {
        return FileController.getAllElements(path, path, 1).sortAndGet();
    }

    public static StorageElementList getAllElements(String path, String rootDir, int index) {

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
                            returnArray.add(se);
                            if (lastModified < se.getLastModified()) {
                                lastModified = se.getLastModified();
                            }
                        }

                        if (se.isDirectory()) {
                            StorageElementList sel = FileController.getAllElements(se.getAbsolutePath(), rootDir, index);
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

    public static void compareJSONFiles(String sourcePath, String targetPath, String compareFilePath, boolean isHardSync, boolean slowMode) {

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

        getNotContainedElements(sourceElements, targetElements, false, isHardSync, slowMode, comparedList, deleteList);

        if (isHardSync) {
            getNotContainedElements(targetElements, sourceElements, true, isHardSync, slowMode, comparedList, deleteList);
        }

        compareFile.setCopyList(comparedList);
        compareFile.setDeleteList(deleteList);
        compareFile.setNewTargetList(newTargetList);
        compareFile.saveAsJSON(compareFilePath);
    }


    private static void getNotContainedElements(StorageElement[] firstElements, StorageElement[] secondElements, boolean collectElementsToDelete, boolean isHardSync, boolean slowMode, List<StorageElement> comparedList, List<StorageElement> deleteList) {

        //TODO: einzelne datei in vielen neuen unterordnern --> legt jeden ordner zum Kopieren an

        int minRgt = 1; // because the first source Element should be ignored
        int indexInTarget;

        StorageElement element2, tmpElement2;
        boolean nameFound, isDifferent, sameName, laterChangeDate, earlierChangeDate, sameSize, sameChildrenCount, hasChildren, renamed;

        List<StorageElement> returnList = new ArrayList<>();


        for (StorageElement element1 : firstElements) {
            if (element1 != null) {
                if (element1.getLft() > minRgt) {
                    indexInTarget = 0;
                    element2 = new StorageElement();
                    nameFound = false;

                    // find the source File in the target
                    // contains target the sourceElement with the specific property 'relative_path' (name)
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

                    // if an element is found with path
                    // then check if name, size or changed_at is different
                    if (nameFound) {

                        element2.setIsCompared(true);
                        element1.setIsCompared(true);

                        isDifferent = false;

                        if (!collectElementsToDelete) {
                            laterChangeDate = element1.getLastModified() > element2.getLastModified();
                            if (laterChangeDate) {
                                isDifferent = true;
                                element1.setDifferentChanged(true);
                            }
                            if (isHardSync) {
                                earlierChangeDate = element1.getLastModified() < element2.getLastModified();
                                if (earlierChangeDate) {
                                    isDifferent = true;
                                    element1.setDifferentChanged(true);
                                }
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

    public static void sync(String compareFilePath) throws Exception {
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
