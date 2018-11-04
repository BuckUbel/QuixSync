package controller;

import logger.Logger;
import models.*;

import java.io.File;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.*;

import java.util.ArrayList;
import java.util.List;

class FileController {

    static void printElements(StorageElementList se) {
        FileController.printElements(se, false);
    }

    static void printElements(StorageElementList se, boolean printingAll) {

        double count = 0;
        StorageElement tmpSE;

        for (int i = 0; i < se.getElements().size(); i++) {

            tmpSE = se.getElements().get(i);
            String extra = "";

            if (tmpSE.isDifferentChanged()) {
                extra += " DifferentChanged ";
            }

            if (tmpSE.isDifferentName()) {
                extra += " DifferentName ";
            }

            if (tmpSE.isDifferentSize()) {
                extra += " DifferentSize ";
            }

            if (tmpSE.isNewFile()) {
                extra += " New File ";
            }

            if (printingAll) {
                Logger.print("[" + i + "]: " + tmpSE.getRelativePath() + " [" + tmpSE.getFileSizeFormatted() + "]" + " [" + tmpSE.getLastModified() + "]" + extra);
            }

            count += tmpSE.getFileSize();
        }

        Logger.print("----------------------");
        Logger.print(se.getElements().size() + " Dateien");
        Logger.print(FileSize.getFormattedString(count) + " Speichermenge");
    }

    static void writeElementsInFile(StorageElementList se) {
        FileController.writeElementsInFile(se, "testData\\output2.txt");
    }

    static void writeElementsInFile(StorageElementList se, String path) {
        se.saveAsJSON(path);
    }

    static StorageElementList getAllElements(String path) {
        return FileController.getAllElements(path, path, 1).sortAndGet();
    }

    static StorageElementList getAllElements(String path, String rootDir, int index) {

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
                Logger.printErr("keine Elemente vorhanden: " + e);
            }
        }

        return new StorageElementList(returnArray, rootDir, index, lastModified);
    }

    static String compareJSONFiles(String sourcePath, String targetPath, boolean isHardSync, boolean withRenaming) {

        // TODO: create two ways, a safe way with renaming detection, and another way without this
        // the other way is faster, because there will skip folders with a earlier modified date
        // the problem appeared during nesting sets
        //        TODO: Testing --> !!!

        // Was ist mit Dateien die auf B nochmal verändert wurden? Werden diese überschrieben? --> logisch müssten sie es
        // --> sie werden ignoriert -->
        // TODO: new List for files, which are on the target dir newer as in the source dir

        String compareFilePath = "";

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

        comparedList = getNotContainedElements(sourceElements, targetElements, false, withRenaming);

        if (isHardSync) {
            deleteList = getNotContainedElements(targetElements, sourceElements, true, withRenaming);
        }

        compareFile.setCopyList(comparedList);
        compareFile.setDeleteList(deleteList);
        compareFile.setNewTargetList(newTargetList);
        compareFile.saveAsJSON("testData\\outputC.json");
        return compareFilePath;
    }



    private static List<StorageElement> getNotContainedElements(StorageElement[] sourceElements, StorageElement[] targetElements, boolean onlyNewData, boolean withRenaming) {

        int minRgt = 1; // because the first source Element should be ignored
        int indexInTarget;

        StorageElement target, tmpTarget;
        boolean changedAtFound, nameFound, isDifferent, sameName, laterChangeDate, sameChangeDate, earlierChangeDate, sameSize, sameChildrenCount, isDirAndDiffChilds;

        List<StorageElement> returnList = new ArrayList<>();


        for (StorageElement source : sourceElements) {
            if (source != null) {
                if (source.getLft() > minRgt) {
                    indexInTarget = 0;
                    target = new StorageElement();

                    changedAtFound = false;
                    nameFound = false;

                    // find the source File in the target

                    // Try 1
                    // contains target the sourceElement with the specific property 'changed_at'
                    if (withRenaming) {
                        while (indexInTarget < targetElements.length) {
                            tmpTarget = targetElements[indexInTarget];
                            if (tmpTarget != null) {
                                if (tmpTarget.getLastModified() == source.getLastModified()) {
                                    target = tmpTarget;
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
                        while (indexInTarget < targetElements.length) {
                            tmpTarget = targetElements[indexInTarget];
                            if (tmpTarget != null) {
                                if (tmpTarget.getRelativePath().equals(source.getRelativePath())) {
                                    target = tmpTarget;
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

                        target.setIsCompared(true);
                        source.setIsCompared(true);

                        isDifferent = false;

                        if (!nameFound) {
                            sameName = source.getRelativePath().equals(target.getRelativePath());
                            if (!sameName) {
                                isDifferent = true;
                                source.setDifferentName(true);
                            }
                        }

                        if (!changedAtFound) {
                            laterChangeDate = source.getLastModified() > target.getLastModified();
                            if (laterChangeDate) {
                                isDifferent = true;
                                source.setDifferentChanged(true);
                            }
                            sameChangeDate = source.getLastModified() == target.getLastModified();
                            if (sameChangeDate) {
                                // ???
                            }
                            earlierChangeDate = source.getLastModified() < target.getLastModified();
                            if (earlierChangeDate) {
                                // newTargetList.add(source);
                                isDifferent = true;
                                source.setDifferentChanged(true);
                            }
                        }

                        sameSize = source.getFileSize() == target.getFileSize();
                        if (!sameSize) {
                            isDifferent = true;
                            source.setDifferentSize(true);
                        }

                        sameChildrenCount = source.getChildrenCount() == target.getChildrenCount();
                        if (!sameChildrenCount) {
                            isDifferent = true;
                            source.setDifferentChildrenCount(true);
                        }

                        // In cases where there is a new file in B,
                        // the different number of children should not result in copying a folder.
                        isDirAndDiffChilds = source.isDifferentChildrenCount() && source.isDir();

                        if (isDifferent) {
                            if (!onlyNewData) {
                                if (!(isDirAndDiffChilds)) {
                                    returnList.add(source);
                                }
                            }
                        } else {
                            if (!withRenaming) {
                                // file will be ignored
                                // if dir, all files in them will be ignored
                                minRgt = source.getRgt();
                            }
                        }
                        targetElements[indexInTarget] = null;
                    } else {
                        source.setIsNewFile(true);
                        returnList.add(source);
                    }
                }
            }
        }
        return returnList;
    }

    static boolean sync(String compareFilePath) throws Exception {
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

        return true;
    }
}
