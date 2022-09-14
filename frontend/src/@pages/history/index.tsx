import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import PageTemplate from '@/@components/@shared/PageTemplate';
import { getPrevURL } from '@/@components/@shared/PrevPathMemoization';
import UserHistoryList from '@/@components/user/UserHistoryList';
import { useFetchUserHistoryList, useReadHistory } from '@/@hooks/@queries/user';
import { useReadAllHistory } from '@/@hooks/business/user';
import { UserHistory } from '@/types/client/user';
import { couponListDetailPageRegExp } from '@/utils/regularExpression';

const UserHistoryPage = () => {
  const navigate = useNavigate();

  const { historyList, isReadAll, refetch } = useFetchUserHistoryList();
  const { readAllHistory } = useReadAllHistory();
  const { readHistory } = useReadHistory();

  useEffect(() => {
    const prevURL = getPrevURL();

    if (couponListDetailPageRegExp.test(prevURL)) {
      return;
    }

    refetch();
  }, []);

  useEffect(() => {
    if (isReadAll) {
      return;
    }

    const readUserHisory = async () => {
      await readAllHistory();
    };

    readUserHisory();
  }, [isReadAll]);

  const onClickHistoryItem = ({ id, couponId, isRead }: UserHistory) => {
    if (!isRead) {
      readHistory(id);
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
