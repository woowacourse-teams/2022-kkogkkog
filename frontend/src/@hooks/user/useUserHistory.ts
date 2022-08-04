import { useReadHistoryMutation } from '@/@hooks/@queries/user';

export const useUserHistory = () => {
  const readHistoryMutation = useReadHistoryMutation();

  const readHistory = (id: number) => {
    readHistoryMutation.mutate({ id });
  };

  return {
    readHistory,
  };
};
