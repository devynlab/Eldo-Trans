package io.devynlab.eldotrans.generic.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class ObjectListWrapper<T> implements Serializable {
  private List<T> list = new ArrayList<>();
  private int total;
  private int currentPage = 1;
  private int pageSize = 1;

  public List<T> getList() {
    return list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getNextPage() {
    return currentPage + 1;
  }

  public int getPreviousPage() {
    return currentPage - 1;
  }

  public int getFirstPage() {
    return 1;
  }

  public long getLastPage() {
    long lastPage = (total / pageSize);
    if (total % pageSize == 0) lastPage--;
    return lastPage + 1;
  }

  public int getIndexBegin() {
    return (getCurrentPage() - 1) * getPageSize();
  }

  public int getIndexEnd() {
    int firstIndex = getIndexBegin();
    int pageIndex = getCurrentPage() - 1;
    int lastIndex = getTotal() - 1;
    final int min = Math.min(firstIndex + pageIndex, lastIndex);
    return Math.max(min, 0);
  }

  public boolean isPreviousPageAvailable() {
    return getIndexBegin() + 1 > getPageSize();
  }

  public boolean isNextPageAvailable() {
    return total - 1 > getIndexEnd();
  }

  public boolean isSeveralPages() {
    return getTotal() != 0 && getTotal() > getPageSize();
  }

  public int getFirstRecord() {
    return (pageSize * currentPage) - pageSize + 1;
  }

  public int getLastRecord() {
    return Math.min((currentPage * pageSize + pageSize), total);
  }

  public String toString() {
    return "Pager - Records: " + getTotal() + ", Page size: " + getPageSize() + ",Current Page: " + getCurrentPage() +
        ", Index Range Begin: " + getIndexBegin() + ", Index Range End  " + getIndexEnd() + " Last Record: " + getLastRecord() + " ";
  }
}
