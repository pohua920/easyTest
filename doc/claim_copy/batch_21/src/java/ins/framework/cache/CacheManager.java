package ins.framework.cache;

/**
 * mantis：CLM0276，處理人員：DP0713，需求單編號：新核心-修正正在處理立案任務的[提交]按鈕問題 
 * 緩存工廠類
 * @author Sinosoft
 */
public class CacheManager {
	/**
	 * 默認為HASHMAP_CACHE
	 */
	private static int cacheType = CacheService.HASHMAP_CACHE;

	public static int getCacheType() {
		return cacheType;
	}

	/**
	 * 設置緩存實現類型變量
	 * @param cacheType 緩存類型
	 */
	public static void setCacheType(int cacheType) {
		CacheManager.cacheType = cacheType;
		switch (cacheType) {
		case CacheService.EHCACHE_CACHE:
			// System.out.println("CacheManager.setCacheType(CacheService.EHCACHE_CACHE)");
			break;
		case CacheService.HASHMAP_CACHE:
			// System.out.println("CacheManager.setCacheType(CacheService.HASHMAP_CACHE)");
			break;
		default:
			// System.out.println("Unsupport cacheType,use defalut CacheService.HASHMAP_CACHE");
			CacheManager.cacheType = CacheService.HASHMAP_CACHE;
			break;
		}
	}

	/**
	 * 獲取緩存實例
	 * @param cacheManagerName 緩存管理器名稱
	 * @return 返回一個CacheService對象
	 */
	public static CacheService getInstance(String cacheManagerName) {
		CacheService cacheService;
		if (cacheType == CacheService.EHCACHE_CACHE) {
			cacheService = EhCacheCacheManager.getInstance(cacheManagerName);
		} else {
			cacheService = HashMapCacheManager.getInstance(cacheManagerName);
		}
		return cacheService;
	}

	/**
	 * 清空所有緩存管理器
	 */
	public static void clearAllCacheManager() {
		if (cacheType == CacheService.EHCACHE_CACHE) {
			EhCacheCacheManager.clearAllCacheManagerStatic();
		} else {
			HashMapCacheManager.clearAllCacheManagerStatic();
		}
		
		CacheService cacheManager = CacheManager.getInstance("PowerServiceSpringImpl");
		if(null!=cacheManager){
			for(Object str : cacheManager.getKeys()){
				cacheManager.clearCacheManager(str.toString());
			}
			cacheManager.clearAllCacheManager();
		}
	}
	/**
	 * 獲取所有緩存管理器名稱
	 * @return 返回一個數組
	 */
	public static String[] getAllCacheManagerName()
	  {
	    if (cacheType == 2)
	      return EhCacheCacheManager.getAllCacheManagerNameStatic();

	    return HashMapCacheManager.getAllCacheManagerNameStatic();
	  }
}
