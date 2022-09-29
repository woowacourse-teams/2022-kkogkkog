import UserHistoryItem from '@/@components/user/UserHistoryItem';
import { UserCouponHistory } from '@/types/user/client';

import * as Styled from './style';

interface UserHistoryListProps {
  historyList: UserCouponHistory[];
  onClickHistoryItem: (userHistory: UserCouponHistory) => void;
}

const UserHistoryList = (props: UserHistoryListProps) => {
  const { historyList, onClickHistoryItem } = props;

  return (
    <Styled.Root>
      {historyList?.map(history => (
        <UserHistoryItem
          key={history.id}
          history={history}
          onClick={() => onClickHistoryItem(history)}
        />
      ))}
    </Styled.Root>
  );
};

export default UserHistoryList;
