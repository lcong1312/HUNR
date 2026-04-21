package com.ngocrong.network;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cache để lưu danh sách resource files theo zoomLevel
 */
public class ResourceCache {
    private static ResourceCache instance;
    private final Map<Integer, ArrayList<String>> resourceCache = new ConcurrentHashMap<>();
    private final Map<Integer, Long> lastModified = new ConcurrentHashMap<>();

    private ResourceCache() {}

    public static synchronized ResourceCache getInstance() {
        if (instance == null) {
            instance = new ResourceCache();
        }
        return instance;
    }

    /**
     * Lấy danh sách resource files theo zoomLevel với cache
     */
    public ArrayList<String> getResourcePaths(int zoomLevel) {
        String folder = "resources/data/" + zoomLevel;
        String overlayFolder = "resources_extra/data/" + zoomLevel;
        File folderFile = new File(folder);
        File overlayFolderFile = new File(overlayFolder);

        if (!folderFile.exists() && !overlayFolderFile.exists()) {
            return new ArrayList<>();
        }

        long currentModified = folderFile.lastModified() ^ (overlayFolderFile.lastModified() << 1);
        Long cachedModified = lastModified.get(zoomLevel);

        // Kiểm tra cache có hợp lệ không
        if (cachedModified != null && cachedModified == currentModified && resourceCache.containsKey(zoomLevel)) {
            return new ArrayList<>(resourceCache.get(zoomLevel));
        }

        // Build cache mới
        Set<String> pathSet = new LinkedHashSet<>();
        addPath(pathSet, folderFile, folderFile.getPath(), folderFile.getPath());
        addPath(pathSet, overlayFolderFile, overlayFolderFile.getPath(), folderFile.getPath());

        ArrayList<String> paths = new ArrayList<>(pathSet);
        resourceCache.put(zoomLevel, new ArrayList<>(paths));
        lastModified.put(zoomLevel, currentModified);

        return paths;
    }

    /**
     * Đệ quy thêm path vào danh sách
     */
    private void addPath(Set<String> paths, File file, String rootPath, String canonicalRootPath) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isFile()) {
            String relativePath = file.getPath().substring(rootPath.length()).replace('\\', '/');
            String canonicalPath = canonicalRootPath.replace('\\', '/') + relativePath;
            paths.add(canonicalPath);
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    addPath(paths, f, rootPath, canonicalRootPath);
                }
            }
        }
    }

    /**
     * Xóa cache để reload
     */
    public void clearCache(int zoomLevel) {
        resourceCache.remove(zoomLevel);
        lastModified.remove(zoomLevel);
    }

    /**
     * Xóa toàn bộ cache
     */
    public void clearAllCache() {
        resourceCache.clear();
        lastModified.clear();
    }
}
