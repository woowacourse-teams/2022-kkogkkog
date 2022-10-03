import type { QueryKey, SetDataOptions } from 'react-query';
import type { Updater } from 'react-query/types/core/utils';

declare module 'react-query' {
  export class QueryClient {
    setQueryData<TData>(
      queryKey: QueryKey,
      updater: Updater<TData | undefined, TData | undefined>,
      options?: SetDataOptions
    ): TData;
  }
}
