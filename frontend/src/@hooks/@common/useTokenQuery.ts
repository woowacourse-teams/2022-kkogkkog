import { QueryFunction, QueryKey, useQuery, UseQueryOptions, UseQueryResult } from 'react-query';

type Query<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey
> = (
  queryKey: TQueryKey,
  queryFn: QueryFunction<TQueryFnData, TQueryKey>,
  options?: Omit<UseQueryOptions<TQueryFnData, TError, TData, TQueryKey>, 'queryKey' | 'queryFn'>
) => UseQueryResult<TData, TError>;

export const useTokenQuery: Query = (key, fetcher, options) => {
  return useQuery(key, fetcher, {
    ...options,
    onError(error) {
      if (error.response.status === 401) {
        // Token 파기
        // Routing ?
        // DisplayMessage ?
        return;
      }

      options?.onError?.(error);
    },
  });
};
