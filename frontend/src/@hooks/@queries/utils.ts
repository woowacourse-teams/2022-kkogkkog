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
  const useErrorBoundary = options?.useErrorBoundary ?? !options?.onError;

  return useOriginQuery(queryKey, queryFn, {
    ...options,
    useErrorBoundary,
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
  const useErrorBoundary = options?.useErrorBoundary ?? !options?.onError;

  return useOriginMutation(mutationFn, {
    ...options,
    useErrorBoundary,
  });
};
