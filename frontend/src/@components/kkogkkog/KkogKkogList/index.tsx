import KkogKkogItem from '@/@components/kkogkkog/KkogKkogItem';
import { KkogKkog } from '@/types/domain';
import { THUMNAIL } from '@/utils/constants/kkogkkog';

import * as Styled from './style';

interface KkogKkogListProps {
  kkogkkogList: KkogKkog[];
}

const KkogKkogList = ({ kkogkkogList }: KkogKkogListProps) => {
  return (
    <Styled.Root>
      {kkogkkogList.map(kkogkkog => (
        <KkogKkogItem key={kkogkkog.id} {...kkogkkog} thumbnail={THUMNAIL[kkogkkog.type]} />
      ))}
    </Styled.Root>
  );
};

export default KkogKkogList;
