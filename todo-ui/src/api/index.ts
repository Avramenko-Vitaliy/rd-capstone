import axios, { AxiosResponse, Method, ResponseType } from 'axios';
import { GetThunkAPI } from '@reduxjs/toolkit';

import { trimValue } from '@/utils/formater';

import settings from '@/settings';

interface FieldError {
  field: string;
  message: string;
}

interface ErrorBody {
  message: string;
  status: number;
  errors: FieldError[];
}

interface InstanceProps {
  submitFormRequest?: boolean;
  converter?: (response: AxiosResponse) => any;
  onPending?: (arg: any, thunkApi: GetThunkAPI<any>) => void;
  onSuccess?: (arg: any, response: AxiosResponse, thunkApi: GetThunkAPI<any>) => void;
  onFail?: (arg: any, response: AxiosResponse, thunkApi: GetThunkAPI<any>) => void;
}

interface CallApiParams {
  endpoint: string;
  params?: any;
  body?: any;
  headers?: { [key: string]: string | string[] | number | boolean | null };
  method?: Method;
  responseType?: ResponseType;
}

type CreatorParams = (arg: any, thunkApi: GetThunkAPI<any>) => InstanceProps & CallApiParams;

const instance = axios.create({
  timeout: settings.timeout,
});

export const makeQueryParams = (params: any): any => {
  const urlSearchParams = Object.keys(params).reduce((result, key) => {
    result.append(key, params[key]);
    return result;
  }, new URLSearchParams());

  let filter = {};
  urlSearchParams.forEach((value, key) => {
    filter = { ...filter, [key]: value };
  });
  return filter;
};

const transformErrorResponse = (response: AxiosResponse<ErrorBody>) => {
  const errors = response?.data?.errors?.reduce(
    (res, item) => ({
      ...res,
      [item.field]: item.message,
    }),
    {},
  );
  return {
    ...errors,
    _error: response?.data?.message || response?.statusText,
  };
};

const callApi = ({
  headers = {},
  method = 'GET',
  endpoint,
  body,
  params,
  responseType = 'json',
}: CallApiParams): Promise<AxiosResponse> => {
  const data =
    body && !Array.isArray(body) && !(body instanceof FormData)
      ? (Object.keys(body) || []).reduce(
          (result, key) => ({
            ...result,
            [key]: trimValue(body[key]),
          }),
          {},
        )
      : body;
  return instance({
    headers,
    url: endpoint,
    method,
    data,
    params,
    responseType,
  });
};

export const apiThunk = (creator: CreatorParams) => async (arg: any, thunkApi: GetThunkAPI<any>) => {
  const {
    converter = response => response,
    onPending = () => {},
    onSuccess = () => {},
    onFail = () => {},
    headers = {},
    submitFormRequest = false,
    params = {},
    ...other
  } = creator(arg, thunkApi);

  try {
    onPending(arg, thunkApi);
    const response = await callApi({
      ...other,
      params: makeQueryParams(params),
      headers,
    });
    onSuccess(arg, response, thunkApi);
    const result = converter(response);
    return thunkApi.fulfillWithValue(result);
  } catch (e: any) {
    console.error(e);
    onFail(arg, e.response, thunkApi);
    if (!e.response) {
      return thunkApi.rejectWithValue({ errors: e });
    }
    if (submitFormRequest && e.response?.status === 422) {
      return thunkApi.rejectWithValue({ ...e.response, errors: transformErrorResponse(e.response) });
    }
    return thunkApi.rejectWithValue({ errors: e.response });
  }
};
