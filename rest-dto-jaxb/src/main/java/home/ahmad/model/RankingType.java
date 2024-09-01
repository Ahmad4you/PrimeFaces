package home.ahmad.model;


public enum RankingType {

    /**
     * The RankingType of the recommendation.
     */
	OUTSTANDING("outstanding", true, 5),
	EXCELLENT("excellent", true, 4),
	GOOD("good", true, 3),
	AVERAGE("average", true, 2),
	POOR("poor", true, 1),
	DREADFUL("dreadful", true, 0);

    /**
     * A human readable description of the ranking type.
     */
    private final String description;
    
    /**
     * A boolean flag indicating whether the adult-only type can be cached.
     */
    private final boolean cacheable;
    
    private final int orderseq;
    
    private RankingType(String description, boolean cacheable, int orderseq) {
        this.description = description;
        this.cacheable = cacheable;
        this.orderseq = orderseq;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCacheable() {
        return cacheable;
    }
    
    public int getOrderseq() {
    	return orderseq;
    }
    
    @Override
    public String toString() {
    	return description;
    }
}
