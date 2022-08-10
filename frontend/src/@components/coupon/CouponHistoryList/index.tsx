import { CouponHistory } from '@/types/client/coupon';

import CouponListItem from '../CouponListItem';
import * as Styled from './style';

interface CouponHistoryListProps {
  historyList: CouponHistory[];
}

const CouponHistoryList = (props: CouponHistoryListProps) => {
  const { historyList } = props;

  return (
    <Styled.Root>
      {historyList?.map(history => (
        <CouponListItem key={history.id} history={history} />
      ))}
    </Styled.Root>
  );
};

export default CouponHistoryList;
