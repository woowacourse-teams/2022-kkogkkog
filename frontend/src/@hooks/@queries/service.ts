import { AddSlackApp } from '@/apis/service';

import { useMutation } from './utils';

export const useAddSlackAppMutation = () => {
  return useMutation(AddSlackApp);
};
