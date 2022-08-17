import { AxiosError } from 'axios';
import { MutationFunction, useMutation, UseMutationOptions } from 'react-query';
import { useNavigate } from 'react-router-dom';

import { PATH } from '@/Router';

import { useToast } from './useToast';

export const useTokenMutation = <
  TData = unknown,
  TError = unknown,
  TVariables = void,
  TContext = unknown
>(
  mutationFn: MutationFunction<TData, TVariables>,
  options?: Omit<UseMutationOptions<TData, TError, TVariables, TContext>, 'mutationFn'>
) => {
  const { displayMessage } = useToast();
  const navigate = useNavigate();

  return useMutation(mutationFn, {
    ...options,
    onError(error, variables, context) {
      if (error instanceof AxiosError && error.response?.status === 401) {
        localStorage.removeItem('user-token');
        displayMessage('다시 로그인해주세요', true);
        navigate(PATH.LOGIN);

        return;
      }

      options?.onError?.(error, variables, context);
    },
  });
};
