import { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

import PageTemplate from '@/@components/@shared/PageTemplate';
import UserHistoryList from '@/@components/user/UserHistoryList';
import {
  useFetchUserHistoryList,
  useReadAllHistoryMutation,
  useReadHistory,
} from '@/@hooks/@queries/user';
import { UserHistory } from '@/types/client/user';

const UserHistoryPage = () => {
  const navigate = useNavigate();
  const state = useLocation().state as { shouldRefetch: boolean } | null;

  const { historyList, refetch } = useFetchUserHistoryList();

  const { mutate: readAllHistory } = useReadAllHistoryMutation();
  const readHistory = useReadHistory();

  useEffect(() => {
    if (state?.shouldRefetch) {
      refetch();
      readAllHistory();
      window.history.replaceState({}, document.title);
    }
  }, [state, refetch, readAllHistory]);

  const onClickHistoryItem = ({ id, couponId, isRead }: UserHistory) => {
    if (!isRead) {
      readHistory({ id });
    }
    navigate(`/coupon-list/${couponId}`);
  };

  return (
    <PageTemplate title='알림함'>
      {historyList && (
        <UserHistoryList historyList={historyList} onClickHistoryItem={onClickHistoryItem} />
      )}
    </PageTemplate>
  );
};

export default UserHistoryPage;
