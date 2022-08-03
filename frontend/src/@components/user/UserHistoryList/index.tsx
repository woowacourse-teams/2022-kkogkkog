import UserHistoryItem from '@/@components/user/UserHistoryItem';

import * as Styled from './style';

const UserHistoryList = (props: any) => {
  const { historyList } = props;

  return (
    <Styled.Root>
      {historyList?.map((history: any) => (
        <UserHistoryItem key={history.id} history={history} />
      ))}
    </Styled.Root>
  );
};

export default UserHistoryList;
