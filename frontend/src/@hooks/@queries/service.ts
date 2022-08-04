import { useMutation } from 'react-query';

import { AddSlackApp } from '@/apis/service';

export const useAddSlackAppMutation = () => {
  return useMutation(AddSlackApp);
};
