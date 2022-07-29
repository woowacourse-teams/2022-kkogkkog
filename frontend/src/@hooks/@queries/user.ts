import { useMutation } from 'react-query';

import { oAuthLogin } from '@/apis/user';

export const useLoginMutation = () => {
  return useMutation(oAuthLogin);
};
