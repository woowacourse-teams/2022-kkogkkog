import UserHistoryItem from '@/@components/user/UserHistoryItem';
import { UserHistory } from '@/types/client/user';

import * as Styled from './style';

interface UserHistoryListProps {
  historyList: UserHistory[];
  onClickHistoryItem: (id: number, isRead: boolean) => void;
}

const UserHistoryList = (props: UserHistoryListProps) => {
  const { historyList, onClickHistoryItem } = props;

  return (
    <Styled.Root>
      {historyList?.map(history => (
        <UserHistoryItem
          key={history.id}
          history={history}
          onClick={() => onClickHistoryItem(history.id, history.isRead)}
        />
      ))}
    </Styled.Root>
  );
};

export default UserHistoryList;
