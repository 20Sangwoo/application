package com.example.application.domain.post.svc;

import java.util.List;

public class PageResult<T> {
  private List<T> data;
  private int totalCount;
  private int currentPage;
  private int pageSize;
  private int startPage;
  private int endPage;

  public PageResult(List<T> data, int totalCount, int currentPage, int pageSize) {
    this.data = data;
    this.totalCount = totalCount;
    this.currentPage = currentPage;
    this.pageSize = pageSize;

    int totalPages = getTotalPages();
    int blockSize = 10; // 한 페이지 블록당 보여줄 페이지 수

    this.startPage = ((currentPage - 1) / blockSize) * blockSize + 1;
    this.endPage = Math.min(startPage + blockSize - 1, totalPages);
  }

  public int getTotalPages() {
    return (int) Math.ceil((double) totalCount / pageSize);
  }

  // Getters
  public List<T> getData() { return data; }
  public int getTotalCount() { return totalCount; }
  public int getCurrentPage() { return currentPage; }
  public int getPageSize() { return pageSize; }
  public int getStartPage() { return startPage; }
  public int getEndPage() { return endPage; }
}