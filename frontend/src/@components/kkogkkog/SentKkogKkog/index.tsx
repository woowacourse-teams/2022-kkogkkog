import { useMemo } from 'react';
import { useLocation } from 'react-router-dom';

import ListFilter from '@/@components/@shared/ListFilter';
import KkogKkogList from '@/@components/kkogkkog/KkogKkogList';
import { useStatus } from '@/@hooks/@common/useStatus';
import { KkogKKogResponse } from '@/types/remote/response';

import * as Styled from './style';

interface SentkogKkogProps {
  kkogkkogList: KkogKKogResponse[] | undefined;
}

const filterOption = ['요청', '대기'] as const;

export type SentKkogKkogFilterOptionType = typeof filterOption[number];

const SentKkogKkog = (props: SentkogKkogProps) => {
  const { kkogkkogList } = props;

  const { state } = useLocation() as { state: { action: string } };

  const { status, changeStatus } = useStatus<SentKkogKkogFilterOptionType>(
    state?.action === 'create' ? '대기' : '요청'
  );

  const onClickFilterButton = (status: SentKkogKkogFilterOptionType) => {
    changeStatus(status);
  };

  const parsedKkogKkogList = useMemo(
    () =>
      kkogkkogList?.reduce(
        (prev, kkogkkog) => {
          const key = kkogkkog.couponStatus;

          return { ...prev, [key]: [...prev[key], kkogkkog] };
        },
        {
          REQUESTED: [],
          READY: [],
        } as any
      ),
    [kkogkkogList]
  );

  return (
    <Styled.Root>
      <ListFilter<SentKkogKkogFilterOptionType>
        status={status}
        onClickFilterButton={onClickFilterButton}
        options={filterOption}
      />
      {status === '요청' && (
        <Styled.Container>
          <div>
            사용 <span>요청이 온</span> 꼭꼭
          </div>
          <KkogKkogList kkogkkogList={parsedKkogKkogList['REQUESTED']} />
        </Styled.Container>
      )}

      {status === '대기' && (
        <Styled.Container>
          <div>
            사용을 <span>기다리는</span> 꼭꼭
          </div>
          <KkogKkogList kkogkkogList={parsedKkogKkogList['READY']} />
        </Styled.Container>
      )}
    </Styled.Root>
  );
};

export default SentKkogKkog;
