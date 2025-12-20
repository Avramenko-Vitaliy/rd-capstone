import { useCallback, useEffect, useMemo } from 'react';
import { useLocation, useNavigate } from 'react-router';
import queryString, { ParsedQuery } from 'query-string';

import settings from '@/settings';

type FilterParamsType = {
  search?: string;
  exclude?: string[];
};

export interface QueryParam {
  name: string;
  value: any | any[];
}

const DEFAULT_PARAMS = { exclude: [], search: '' };

export const parse = (search: string) => queryString.parse(search, settings.queryFormat);

export function useFilterToQuery({ search = '', exclude = [] }: FilterParamsType = DEFAULT_PARAMS) {
  const navigate = useNavigate();
  const { search: searchParams } = useLocation();

  const query = useMemo(() => parse(searchParams), [searchParams]);

  const filteredQuery = useMemo(() => (
      Object.keys(query)
          .filter(key => !exclude.includes(key))
          .reduce((result, key) => ({ ...result, [key]: query[key] }), {})
  ), [query, exclude]);

  useEffect(() => {
    if (!Object.keys(filteredQuery).length && search) {
      navigate({ search }, { replace: true });
    }
  }, [query, filteredQuery, search]);

  const buildNewQuery = useCallback((params: QueryParam[], preQuery: ParsedQuery<string | number | boolean>): ParsedQuery<string | number | boolean> => (
      params.reduce((result, item) => {
        const { name, value } = item;
        if ((!value && value?.toString() !== 'false') || value === 'all') {
          const newResult = { ...result };
          delete newResult[name];
          return newResult;
        }
        return { ...result, [name]: value };
      }, preQuery)
  ), []);

  const handlePush = useCallback((...params: QueryParam[]) => {
    const newParams = buildNewQuery(params, query);
    navigate({ search: queryString.stringify(newParams, settings.queryFormat) }, { replace: false });
  }, [query, buildNewQuery]);

  const handleReplace = useCallback((...params: QueryParam[]) => {
    const newParams = buildNewQuery(params, query);
    navigate({ search: queryString.stringify(newParams, settings.queryFormat) }, { replace: true });
  }, [query, buildNewQuery]);

  return {
    query: query || {},
    filteredQuery: filteredQuery || {},
    push: handlePush,
    replace: handleReplace,
  };
}
