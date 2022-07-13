import KkogKkogItem from '@/@components/kkogkkog/KkogKkogItem';
import { KkogKkog } from '@/types/domain';
import { THUMBNAIL } from '@/utils/constants/kkogkkog';

import * as Styled from './style';

interface KkogKkogListProps {
  kkogkkogList: KkogKkog[];
}

const KkogKkogList = (props: KkogKkogListProps) => {
  const { kkogkkogList } = props;

  return (
    <Styled.Root>
      {kkogkkogList.map(kkogkkog => (
        <KkogKkogItem key={kkogkkog.id} {...kkogkkog} thumbnail={THUMBNAIL[kkogkkog.couponType]} />
      ))}
    </Styled.Root>
  );
};

export default KkogKkogList;
