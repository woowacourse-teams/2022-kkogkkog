import PageTemplate from '@/@components/@shared/PageTemplate';
import UserHistoryList from '@/@components/user/UserHistoryList';
import { useFetchHistoryList } from '@/@hooks/@queries/user';

const NotificationPage = () => {
  const { historyList } = useFetchHistoryList();

  return (
    <PageTemplate title='알림함'>
      <UserHistoryList historyList={historyList} />
    </PageTemplate>
  );
};

export default NotificationPage;
