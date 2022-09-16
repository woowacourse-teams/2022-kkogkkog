import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import PageTemplate from '@/@components/@shared/PageTemplate';
import UserHistoryList from '@/@components/user/UserHistoryList';
import { useFetchUserHistoryList, useReadHistory } from '@/@hooks/@queries/user';
import { useReadAllHistory } from '@/@hooks/business/user';
import { DYNAMIC_PATH } from '@/Router';
import { getPrevUrl } from '@/storage/session';
import { UserHistory } from '@/types/client/user';
import { couponListDetailPageRegExp } from '@/utils/regularExpression';

const UserHistoryPage = () => {
  const navigate = useNavigate();

  const { historyList, synchronizeServerUserHistory } = useFetchUserHistoryList();
  const { readAllHistory } = useReadAllHistory();
  const { readHistory } = useReadHistory();

  useEffect(() => {
    const prevURL = getPrevUrl();

    if (couponListDetailPageRegExp.test(prevURL)) {
      return;
    }

    synchronizeServerUserHistory();
  }, []);

  useEffect(() => {
    const readUserHisory = async () => {
      await readAllHistory();
    };

    readUserHisory();
  }, [historyList]);

  const onClickHistoryItem = ({ id, couponId, isRead }: UserHistory) => {
    if (!isRead) {
      readHistory(id);
    }

    navigate(DYNAMIC_PATH.COUPON_DETAIL(couponId));
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
