import {
  MutationFunction,
  QueryFunction,
  QueryKey,
  useMutation as useOriginMutation,
  UseMutationOptions,
  useQuery as useOriginQuery,
  UseQueryOptions,
} from 'react-query';

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

export const useMutation = <
  TData = unknown,
  TError = unknown,
  TVariables = void,
  TContext = unknown
>(
  mutationFn: MutationFunction<TData, TVariables>,
  options?: Omit<UseMutationOptions<TData, TError, TVariables, TContext>, 'mutationFn'>
) => {
  return useOriginMutation(mutationFn, {
    ...options,
    useErrorBoundary: !options?.onError,
  });
};
