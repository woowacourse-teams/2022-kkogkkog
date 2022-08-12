import { useNavigate } from 'react-router-dom';

import PageTemplate from '@/@components/@shared/PageTemplate';
import UserHistoryList from '@/@components/user/UserHistoryList';
import { useFetchUserHistoryList } from '@/@hooks/@queries/user';
import { useUserHistory } from '@/@hooks/user/useUserHistory';

const UserHistoryPage = () => {
  const navigate = useNavigate();

  const { historyList } = useFetchUserHistoryList();

  const { readHistory } = useUserHistory();

  const onClickHistoryItem = (id: number, isRead: boolean) => {
    if (!isRead) {
      readHistory(id);
    }
    navigate(`/coupon-list/${id}`);
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
