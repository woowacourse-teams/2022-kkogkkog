import { useMemo, useState } from 'react';
import { useMutation, useQueryClient } from 'react-query';

import ListFilter from '@/@components/@shared/ListFilter';
import KkogKkogList from '@/@components/kkogkkog/KkogKkogList';
import { useStatus } from '@/@hooks/@common/useStatus';
import { changeKkogkkogStatus } from '@/apis/kkogkkog';
import { KkogKKogResponse } from '@/types/remote/response';

import KkogKkogModal from '../KkogKkogModal';
import * as Styled from './style';

const filterOption = ['요청', '승인', '대기', '사용'] as const;

type ReceivedKkogKkogFilterOptionType = typeof filterOption[number];

interface ReceivedKkogKkogProps {
  kkogkkogList: KkogKKogResponse[] | undefined;
}

const ReceivedKkogKkog = (props: ReceivedKkogKkogProps) => {
  const { kkogkkogList } = props;

  const { status, changeStatus } = useStatus<ReceivedKkogKkogFilterOptionType>('요청');

  const [clickedCoupon, setClickedCoupon] = useState<KkogKKogResponse | null>(null);

  const queryClient = useQueryClient();

  const changeStatusMutate = useMutation(changeKkogkkogStatus, {
    onSuccess() {
      queryClient.invalidateQueries('kkogkkogList');
    },
    onError() {
      alert('잘못된 접근입니다. 다시 시도해주세요.');
    },
  });

  const onClickFilterButton = (status: ReceivedKkogKkogFilterOptionType) => {
    changeStatus(status);
  };

  const onClickCoupon = (kkogkkog: KkogKKogResponse) => {
    setClickedCoupon(kkogkkog);
  };

  const onCloseModal = () => {
    setClickedCoupon(null);
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
      modalTitle: '쿠폰 사용 요청을 취소하시겠어요?',
      modalButtons: [
        {
          text: '요청 취소',
          onClick({ id, message }) {
            changeStatusMutate.mutate({ id, body: { couponEvent: 'CANCEL', message } });
          },
        },
      ],
    },
    READY: {
      modalTitle: '쿠폰을 사용하시겠어요?',
      modalButtons: [
        {
          text: '사용 요청',
          onClick({ id, message }) {
            changeStatusMutate.mutate({ id, body: { couponEvent: 'REQUEST', message } });
          },
        },
        {
          text: '사용 완료',
          onClick({ id, message }) {
            console.log('사용 완료!');
            // changeStatusMutate.mutate({ id, body: { couponEvent: 'FINISH', message } });
          },
        },
      ],
    },
    ACCEPTED: {
      modalTitle: '쿠폰 사용하셨나요??',
      modalButtons: [
        {
          text: '사용 완료',
          onClick({ id, message }) {
            console.log('사용 완료!');
            // changeStatusMutate.mutate({ id, body: { couponEvent: 'FINISH', message } });
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
      <ListFilter<ReceivedKkogKkogFilterOptionType>
        status={status}
        onClickFilterButton={onClickFilterButton}
        options={filterOption}
      />
      {status === '요청' && (
        <Styled.Container>
          <div>
            사용 <span>요청을 한</span> 꼭꼭
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
            사용 <span>승인 받은</span> 꼭꼭
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
            사용 <span>하지 않은</span> 꼭꼭
          </div>
          <KkogKkogList kkogkkogList={parsedKkogKkogList['READY']} onClickCoupon={onClickCoupon} />
        </Styled.Container>
      )}

      {status === '사용' && (
        <Styled.Container>
          <div>
            <span>사용한</span> 꼭꼭
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

export default ReceivedKkogKkog;

ReceivedKkogKkog.Skeleton = function Skeleton() {
  return (
    <Styled.Root>
      <ListFilter.Skeleton />
      <KkogKkogList.Skeleton />
    </Styled.Root>
  );
};
