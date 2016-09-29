package com.wuyz.androidutils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by wuyz on 2016/9/29.
 *
 */

public class FileUtils {

    private static final String TAG = "FileUtils";

    public static boolean unZip(String zipPath, String destPath) {
        Log2.d(TAG, "unZip " + zipPath + " to " + destPath);
        if (zipPath == null || zipPath.isEmpty() || destPath == null || destPath.isEmpty()) {
            return false;
        }
        File srcFile = new File(zipPath);
        if (!srcFile.exists() || !srcFile.isFile())
            return false;
        File destFile = new File(destPath);
        if (!destFile.exists()) {
            if (!destFile.mkdirs()) {
                Log2.e(TAG, "mkdir error: " + destFile);
                return false;
            }
        }
        ZipInputStream zipInputStream = null;
        try {
            zipInputStream = new ZipInputStream(new FileInputStream(zipPath));
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
//                Log2.d(TAG, "zip: %s", zipEntry);
                File dest = new File(destFile, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    dest.mkdirs();
                } else {
                    File parent = dest.getParentFile();
                    if (parent != null && !parent.exists()) {
                        parent.mkdirs();
                    }
                    FileOutputStream outputStream = new FileOutputStream(dest);
                    byte[] buffer = new byte[2048];
                    int n;
                    while ((n = zipInputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, n);
                    }
                    outputStream.close();
                }

            }
            zipInputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (zipInputStream != null) {
                try {
                    zipInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static void cleanDir(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                cleanDir(file);
                file.delete();
            }
        }
    }

    public static boolean zipFile(ZipOutputStream outputStream, File file, String parent) {
        if (!file.exists()) {
            return false;
        }
        FileInputStream inputStream = null;
        try {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                outputStream.putNextEntry(new ZipEntry(file.getName() + File.separator));
                outputStream.closeEntry();
                for (File f : files) {
                    zipFile(outputStream, f, file.getName());
                }
            } else if (file.isFile()) {
                if (parent != null && !parent.isEmpty()) {
                    outputStream.putNextEntry(new ZipEntry(parent + File.separator + file.getName()));
                } else {
                    outputStream.putNextEntry(new ZipEntry(file.getName()));
                }
                inputStream = new FileInputStream(file);
                byte[] buffer = new byte[4096];
                int n;
                while ((n = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, n);
                }
                outputStream.closeEntry();
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static boolean zipFiles(String zipFile, File[] srcFiles) {
        ZipOutputStream outputStream = null;
        try {
            outputStream = new ZipOutputStream(new FileOutputStream(zipFile));
            for (File file : srcFiles) {
                if (file.exists()) {
                    zipFile(outputStream, file, "");
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void writeFile(String filePath, String content) {
        if (content == null || content.isEmpty())
            return;
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            if (!parent.mkdirs())
                return;
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file, false);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String readFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int n;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            while ((n = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, n);
            }
            inputStream.close();
            outputStream.close();
            return outputStream.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
