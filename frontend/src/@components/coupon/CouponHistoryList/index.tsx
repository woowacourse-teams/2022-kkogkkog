import { CouponHistory } from '@/types/coupon/client';

import CouponHistoryItem from '../CouponHistoryItem';
import * as Styled from './style';

interface CouponHistoryListProps {
  historyList: CouponHistory[];
}

const CouponHistoryList = (props: CouponHistoryListProps) => {
  const { historyList } = props;

  return (
    <Styled.Root>
      {historyList?.map(history => (
        <CouponHistoryItem key={history.id} history={history} />
      ))}
    </Styled.Root>
  );
};

export default CouponHistoryList;
