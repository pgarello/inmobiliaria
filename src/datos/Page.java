package datos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;

public class Page implements Serializable {
	   
	private static final long serialVersionUID = 6121247053491974766L;
	
	private List results;
    private int pageSize;
    private int page;
    private ScrollableResults scrollableResults;
    private int totalResults = 0;

    /**
     * Construct a new Page. Page numbers are zero-based, so the
     * first page is page 0.
     *
     * @param query the Hibernate Query
     * @param page the page number (zero-based)
     * @param pageSize the number of results to display on the page
     */
    public Page(Query query, int page, int pageSize) {
    	
        this.page = page;
        this.pageSize = pageSize;
        this.results = new ArrayList(); // la inicializo vacia
        
        try {
            scrollableResults = query.scroll();
            scrollableResults.last();
        	totalResults = scrollableResults.getRowNumber();
        	//System.out.println("DATOS:" + totalResults);
            
            /*
             * We set the max results to one more than the specfied pageSize to
             * determine if any more results exist (i.e. if there is a next page
             * to display). The result set is trimmed down to just the pageSize
             * before being displayed later (in getList()).
             */
        	
        	if (pageSize == 0) {
        		results = query.setFirstResult(page * pageSize).list();
        	} else {
        		results = query.setFirstResult(page * pageSize).setMaxResults(pageSize + 1).list();	
        	}        	
        	
        } catch (HibernateException e) {
        	e.printStackTrace();
            System.err.println("Page. Falla en la consulta de DATOS: " + e.getMessage());
            
            // Aca no pude inicializar la página de datos --> como reacciona la aplicación cuando quiere abrir estos datos ....
            
        }

    }

    public boolean isFirstPage() {return page == 0;}
    public boolean isLastPage() {return page >= getLastPageNumber();}
    
    public boolean hasNextPage() {return results.size() > pageSize;}
    public boolean hasPreviousPage() {return page > 0;}

    public int getLastPageNumber() {
        /*
         * We use the Math.floor() method because page numbers are zero-based
         * (i.e. the first page is page 0).
         */
        double totalResults = new Integer(getTotalResults()).doubleValue();
        return new Double(Math.floor(totalResults / pageSize)).intValue();
    }

    public List getList() {
        /*
         * Since we retrieved one more than the specified pageSize when the
         * class was constructed, we now trim it down to the pageSize if a next
         * page exists.
         */
    	
    	if (pageSize == 0) 
    		return results;
    	else 
    		return hasNextPage() ? results.subList(0, pageSize) : results;
    }

    public int getTotalResults() {
        try {
        	//getScrollableResults().last();
        	//totalResults = getScrollableResults().getRowNumber();
        } catch (HibernateException e) {
        	System.err.println("Failed to get last row number from scollable results: " + e.getMessage());
        }
        return totalResults;
    }

    public int getFirstResultNumber() {return page * pageSize + 1;}
    public int getLastResultNumber() {
    	int fullPage = getFirstResultNumber() + pageSize - 1;
        return getTotalResults() < fullPage ? getTotalResults() : fullPage;
    }

    public int getNextPageNumber() {return page + 1;}
    public int getPreviousPageNumber() {return page - 1;}

    protected ScrollableResults getScrollableResults(){
        return scrollableResults;
    }

}