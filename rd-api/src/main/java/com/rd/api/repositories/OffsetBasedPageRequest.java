package com.rd.api.repositories;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class OffsetBasedPageRequest {

    private OffsetBasedPageRequest() {
    }

    public static OffsetBasedPageRequestBuilder builder() {
        return new OffsetBasedPageRequestBuilder();
    }

    public static class OffsetBasedPageRequestBuilder {
        private Sort sort = Sort.unsorted();
        private int limit;
        private int offset;

        public OffsetBasedPageRequestBuilder sort(Sort sort) {
            this.sort = sort;
            return this;
        }

        public OffsetBasedPageRequestBuilder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public OffsetBasedPageRequestBuilder offset(int offset) {
            this.offset = offset;
            return this;
        }

        public Pageable build() {
            return PageRequest.of(offset / limit, limit, sort);
        }
    }
}
