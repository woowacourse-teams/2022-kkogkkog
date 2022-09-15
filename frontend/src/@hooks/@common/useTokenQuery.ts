import { AxiosError } from 'axios';
import { QueryFunction, QueryKey, useQuery, UseQueryOptions } from 'react-query';
import { useNavigate } from 'react-router-dom';

import { PATH } from '@/Router';

import { useToast } from './useToast';

export const useTokenQuery = <
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey
>(
  queryKey: TQueryKey,
  queryFn: QueryFunction<TQueryFnData, TQueryKey>,
  options?: Omit<UseQueryOptions<TQueryFnData, TError, TData, TQueryKey>, 'queryKey' | 'queryFn'>
) => {
  const { displayMessage } = useToast();
  const navigate = useNavigate();

  return useQuery(queryKey, queryFn, {
    ...options,
    onError(error) {
      if (error instanceof AxiosError && error.response?.status === 401) {
        localStorage.removeItem('user-token');
        displayMessage('다시 로그인해주세요', true);
        navigate(PATH.LOGIN);

        return;
      }

      options?.onError?.(error);
    },
  });
};