import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import PageTemplate from '@/@components/@shared/PageTemplate';
import KkogKkogCreateForm from '@/@components/kkogkkog/KkogKkogCreateForm';
import KkogKkogItem from '@/@components/kkogkkog/KkogKkogItem';
import { createKkogkkog } from '@/apis/kkogkkog';
import { PATH } from '@/Router';
import theme from '@/styles/theme';
import { KkogKkogType } from '@/types/domain';
import { THUMBNAIL } from '@/utils/constants/kkogkkog';

export const modifiers = ['재미있게', '활기차게', '한턱쏘는'] as const;

export const colors = [
  theme.colors.white_100,
  theme.colors.primary_100,
  theme.colors.primary_200,
  theme.colors.primary_300,
  theme.colors.primary_400,
  theme.colors.primary_500,
] as const;

export const couponTypes = Object.entries(THUMBNAIL).map(([key, value]) => ({
  type: key as KkogKkogType,
  imageURL: value,
}));

const KkogkkogCreatePage = () => {
  const [senderName, setSenderName] = useState('');
  const [receiverName, setReceiverName] = useState('');
  const [couponType, setCouponType] = useState<KkogKkogType>(couponTypes[0].type);
  const [modifier, setModifier] = useState<typeof modifiers[number]>(modifiers[0]);
  const [color, setColor] = useState<typeof colors[number]>(colors[0]);
  const [message, setMessage] = useState('');

  const navigate = useNavigate();
  const { mutate } = useMutation(createKkogkkog, {
    onSuccess() {
      navigate(PATH.KKOGKKOG_LIST);
    },
  });

  const onChangeSenderName = e => {
    const {
      target: { value },
    } = e;

    setSenderName(value);
  };

  const onChangeReceiverName = e => {
    const {
      target: { value },
    } = e;

    setReceiverName(value);
  };

  const onSelectType = (type: KkogKkogType) => {
    setCouponType(type);
  };

  const onSelectModifier = (modifier: typeof modifiers[number]) => {
    setModifier(modifier);
  };

  const onSelectColor = (color: typeof colors[number]) => {
    setColor(color);
  };

  const onChangeMessage = e => {
    const {
      target: { value },
    } = e;

    setMessage(value);
  };

  const onSubmitCreateForm = e => {
    e.preventDefault();

    if (senderName.length === 0 || receiverName.length === 0) {
      alert('정보를 모두 입력해주세요 !');

      return;
    }

    mutate({
      senderName,
      receiverName,
      backgroundColor: color,
      modifier,
      message,
      couponType,
    });
  };

  return (
    <PageTemplate title='꼭꼭 만들기'>
      <StyledRoot>
        <div
          css={css`
            padding: 25px 0;
          `}
        >
          <KkogKkogItem
            id='1'
            backgroundColor={color}
            modifier={modifier}
            receiverName={receiverName}
            senderName={senderName}
            thumbnail={THUMBNAIL[couponType]}
            couponType={couponType}
          />
        </div>
        <KkogKkogCreateForm
          currentSenderName={senderName}
          onChangeSenderName={onChangeSenderName}
          currentReceiverName={receiverName}
          onChangeReceiverName={onChangeReceiverName}
          currentType={couponType}
          onSelectType={onSelectType}
          currentModifier={modifier}
          onSelectModifier={onSelectModifier}
          currentColor={color}
          onSelectColor={onSelectColor}
          currentMessage={message}
          onChangeMessage={onChangeMessage}
          onSubmitCreateForm={onSubmitCreateForm}
        />
      </StyledRoot>
    </PageTemplate>
  );
};

export default KkogkkogCreatePage;

export const StyledRoot = styled.div`
  padding: 0 20px 40px 20px;

  border-radius: 4px;
`;
