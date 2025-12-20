import { useCallback, useEffect, useRef, useState } from 'react';
import InputAdornment from '@mui/material/InputAdornment';
import SearchIcon from '@mui/icons-material/Search';
import InputComponent from '@/components/inputs/InputComponent';
import { QueryParam, useFilterToQuery } from '@/components/filters/hooks/filterToQuery';
import settings from '@/settings';

interface ISearchFilterProps {
  fieldName: string;
  fullWidth?: boolean;
}

function SearchFilter(props: ISearchFilterProps) {
  const timerRef = useRef<number>(null);

  const { query, replace } = useFilterToQuery();

  const [value, setValue] = useState<string>(query[props.fieldName] as string || '');

  useEffect(() => {
    if (!query[props.fieldName]) {
      setValue('');
    }
  }, [query[props.fieldName]]);

  const changeQueryParams = useCallback((inputValue: string) => {
    const newQueryParams: QueryParam[] = [{ name: props.fieldName, value: inputValue }];
    replace(...newQueryParams);
  }, [props.fieldName, query, replace]);

  const handleChange = useCallback(e => {
    e.persist();
    setValue(e.target.value);
    if (timerRef.current) {
      clearTimeout(timerRef.current);
    }
    timerRef.current = window.setTimeout(() => changeQueryParams(e.target.value), settings.searchDelay);
  }, [timerRef.current, props.fieldName, changeQueryParams]);

  const handleClear = useCallback(() => {
    setValue('');
    changeQueryParams('');
  }, [props.fieldName, changeQueryParams]);

  return (
    <InputComponent
      clearable
      fullWidth={props.fullWidth}
      variant="outlined"
      color="primary"
      value={value || ''}
      name={props.fieldName}
      placeholder="Search"
      slotProps={{
        input: {
          startAdornment: (
            <InputAdornment position="start">
              <SearchIcon color="primary" />
            </InputAdornment>
          ),
        },
      }}
      onClear={handleClear}
      onChange={handleChange}
    />
  );
}

export default SearchFilter;
