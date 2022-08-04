import PageTemplate from '@/@components/@shared/PageTemplate';
import UserHistoryList from '@/@components/user/UserHistoryList';
import { useFetchUserHistoryList } from '@/@hooks/@queries/user';
import { useUserHistory } from '@/@hooks/user/useUserHistory';

const UserHistoryPage = () => {
  const { historyList } = useFetchUserHistoryList();

  const { readHistory } = useUserHistory();

  const onClickHistoryItem = (id: number, isRead: boolean) => {
    if (!isRead) {
      readHistory(id);
    }
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
