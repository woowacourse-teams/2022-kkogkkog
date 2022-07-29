import { useMutation } from 'react-query';

import { OAuthLogin } from '@/apis/user';

export const useLoginMutation = () => {
  return useMutation(OAuthLogin);
};
