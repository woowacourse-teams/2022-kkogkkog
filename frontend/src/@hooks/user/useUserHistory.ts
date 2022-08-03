import { useReadHistoryMutation } from '@/@hooks/@queries/user';

export const useUserHistory = () => {
  const readHistoryMutation = useReadHistoryMutation();

  const readHistory = (id: number, isRead: boolean) => {
    if (!isRead) {
      readHistoryMutation.mutate({ id });
    }
  };

  return {
    readHistory,
  };
};
