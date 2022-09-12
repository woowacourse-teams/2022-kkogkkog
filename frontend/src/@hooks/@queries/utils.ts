import { QueryFunction, QueryKey, useQuery as useOriginQuery, UseQueryOptions } from 'react-query';

export const useQuery = <
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey
>(
  queryKey: TQueryKey,
  queryFn: QueryFunction<TQueryFnData, TQueryKey>,
  options?: Omit<UseQueryOptions<TQueryFnData, TError, TData, TQueryKey>, 'queryKey' | 'queryFn'>
) => {
  return useOriginQuery(queryKey, queryFn, {
    ...options,
    useErrorBoundary: !options?.onError,
  });
};
