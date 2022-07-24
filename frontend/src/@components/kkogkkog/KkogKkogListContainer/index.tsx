import { useMemo } from 'react';
import { useLocation } from 'react-router-dom';

import ListFilter from '@/@components/@shared/ListFilter';
import KkogKkogList from '@/@components/kkogkkog/KkogKkogList';
import KkogKkogModalJuunzzi from '@/@components/kkogkkog/KkogKkogModalJuunzzi';
import { useStatus } from '@/@hooks/@common/useStatus';
import useKkogKkogModal from '@/@hooks/kkogkkog/useKkogKkogModal';
import { KkogKKogResponse } from '@/types/remote/response';

import * as Styled from './style';

interface KkogKkogListContainerProps {
  kkogkkogList: KkogKKogResponse[] | undefined;
}

const filterOption = ['요청', '승인', '대기', '사용'] as const;

export type KkogKkogListContainerFilterOptionType = typeof filterOption[number];

const KkogKkogListContainer = (props: KkogKkogListContainerProps) => {
  const { kkogkkogList } = props;

  const { state } = useLocation() as { state: { action: string } };

  const { status, changeStatus } = useStatus<KkogKkogListContainerFilterOptionType>(
    state?.action === 'create' ? '대기' : '요청'
  );

  const { clickedKkogKkog, clickKkogKkog, isShowModal, closeModal } = useKkogKkogModal();

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
          ACCEPTED: [],
          FINISHED: [],
        } as any
      ),
    [kkogkkogList]
  );

  const onClickFilterButton = (status: KkogKkogListContainerFilterOptionType) => {
    changeStatus(status);
  };

  return (
    <Styled.Root>
      <ListFilter<KkogKkogListContainerFilterOptionType>
        status={status}
        onClickFilterButton={onClickFilterButton}
        options={filterOption}
      />
      {status === '요청' && (
        <Styled.Container>
          <div>
            사용 <span>요청이 온</span> 꼭꼭
          </div>
          <KkogKkogList
            kkogkkogList={parsedKkogKkogList['REQUESTED']}
            clickKkogKkog={clickKkogKkog}
          />
        </Styled.Container>
      )}

      {status === '승인' && (
        <Styled.Container>
          <div>
            사용 <span>승인한</span> 꼭꼭
          </div>
          <KkogKkogList
            kkogkkogList={parsedKkogKkogList['ACCEPTED']}
            clickKkogKkog={clickKkogKkog}
          />
        </Styled.Container>
      )}

      {status === '대기' && (
        <Styled.Container>
          <div>
            사용을 <span>기다리는</span> 꼭꼭
          </div>
          <KkogKkogList kkogkkogList={parsedKkogKkogList['READY']} clickKkogKkog={clickKkogKkog} />
        </Styled.Container>
      )}

      {status === '사용' && (
        <Styled.Container>
          <div>
            <span>사용된</span> 꼭꼭
          </div>
          <KkogKkogList
            kkogkkogList={parsedKkogKkogList['FINISHED']}
            clickKkogKkog={clickKkogKkog}
          />
        </Styled.Container>
      )}

      {isShowModal && clickedKkogKkog && (
        <KkogKkogModalJuunzzi kkogkkog={clickedKkogKkog} closeModal={closeModal} />
      )}
    </Styled.Root>
  );
};

export default KkogKkogListContainer;

KkogKkogListContainer.Skeleton = function Skeleton() {
  return (
    <Styled.Root>
      <ListFilter.Skeleton />
      <KkogKkogList.Skeleton />
    </Styled.Root>
  );
};
