import { useMemo, useState } from 'react';
import { useLocation } from 'react-router-dom';

import ListFilter from '@/@components/@shared/ListFilter';
import KkogKkogList from '@/@components/kkogkkog/KkogKkogList';
import { useStatus } from '@/@hooks/@common/useStatus';
import { useChangeKkogKkogStatus } from '@/@hooks/kkogkkog/useChangeKkogKkogStatus';
import { KkogKKogResponse } from '@/types/remote/response';

import KkogKkogModal from '../KkogKkogModal';
import * as Styled from './style';

interface SentkogKkogProps {
  kkogkkogList: KkogKKogResponse[] | undefined;
}

const filterOption = ['요청', '승인', '대기', '사용'] as const;

export type SentKkogKkogFilterOptionType = typeof filterOption[number];

const SentKkogKkog = (props: SentkogKkogProps) => {
  const { kkogkkogList } = props;

  const { state } = useLocation() as { state: { action: string } };

  const [clickedCoupon, setClickedCoupon] = useState<KkogKKogResponse | null>(null);

  const { status, changeStatus } = useStatus<SentKkogKkogFilterOptionType>(
    state?.action === 'create' ? '대기' : '요청'
  );

  const changeKkogKkogStatusMutation = useChangeKkogKkogStatus();

  const onClickCoupon = (kkogkkog: KkogKKogResponse) => {
    setClickedCoupon(kkogkkog);
  };

  const onCloseModal = () => {
    setClickedCoupon(null);
  };

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
          ACCEPTED: [],
          FINISHED: [],
        } as any
      ),
    [kkogkkogList]
  );

  const modalType: Record<
    string,
    {
      modalTitle: string;
      modalButtons?: { text: string; onClick: (args: { id: number; message?: string }) => void }[];
    }
  > = {
    REQUESTED: {
      modalTitle: '쿠폰 사용 요청을 승인하시겠어요?',
      modalButtons: [
        {
          text: '거절',
          onClick({ id, message }) {
            changeKkogKkogStatusMutation.mutate({ id, body: { couponEvent: 'DECLINE', message } });
          },
        },
        {
          text: '승인',
          onClick({ id, message }) {
            changeKkogKkogStatusMutation.mutate({ id, body: { couponEvent: 'ACCEPT', message } });
          },
        },
      ],
    },
    // 현재 Sender는 쿠폰 사용을 요청할 수 없다.
    READY: {
      modalTitle: '보낸 쿠폰입니다.',
    },
    ACCEPTED: {
      modalTitle: '쿠폰 사용하셨나요??',
      modalButtons: [
        {
          text: '사용 완료',
          onClick({ id, message }) {
            console.log('사용 완료!');
            // changeKkogKkogStatusMutation.mutate({ id, body: { couponEvent: 'FINISH', message } });
          },
        },
      ],
    },
    FINISHED: {
      modalTitle: '이미 사용한 쿠폰입니다.',
    },
  };

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
          <KkogKkogList
            kkogkkogList={parsedKkogKkogList['REQUESTED']}
            onClickCoupon={onClickCoupon}
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
            onClickCoupon={onClickCoupon}
          />
        </Styled.Container>
      )}

      {status === '대기' && (
        <Styled.Container>
          <div>
            사용을 <span>기다리는</span> 꼭꼭
          </div>
          <KkogKkogList kkogkkogList={parsedKkogKkogList['READY']} onClickCoupon={onClickCoupon} />
        </Styled.Container>
      )}

      {status === '사용' && (
        <Styled.Container>
          <div>
            <span>사용된</span> 꼭꼭
          </div>
          <KkogKkogList
            kkogkkogList={parsedKkogKkogList['FINISHED']}
            onClickCoupon={onClickCoupon}
          />
        </Styled.Container>
      )}
      {clickedCoupon && (
        <KkogKkogModal
          kkogkkog={clickedCoupon}
          onCloseModal={onCloseModal}
          {...modalType[clickedCoupon.couponStatus]}
        />
      )}
    </Styled.Root>
  );
};

export default SentKkogKkog;

SentKkogKkog.Skeleton = function Skeleton() {
  return (
    <Styled.Root>
      <ListFilter.Skeleton />
      <KkogKkogList.Skeleton />
    </Styled.Root>
  );
};
