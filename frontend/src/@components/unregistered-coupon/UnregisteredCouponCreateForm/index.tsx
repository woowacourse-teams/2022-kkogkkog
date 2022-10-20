import { ChangeEventHandler, FormEventHandler, MouseEventHandler } from 'react';
import { Link } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import Input from '@/@components/@shared/Input';
import SelectInput from '@/@components/@shared/SelectInput';
import { usePreventReload } from '@/@hooks/@common/usePreventReload';
import { PATH } from '@/Router';
import {
  COUPON_ENG_TYPE,
  COUPON_HASHTAGS,
  couponHashtags,
  couponTypeCollection,
  THUMBNAIL,
} from '@/types/coupon/client';

import * as Styled from './style';

interface UnregisteredCouponCreateFormProps {
  currentCouponCount: number;
  currentCouponType: COUPON_ENG_TYPE;
  currentCouponTag: COUPON_HASHTAGS;
  currentCouponMessage: string;
  onClickCouponCountUpdateButton: (count: number) => MouseEventHandler<HTMLButtonElement>;
  onSelectCouponType: (type: COUPON_ENG_TYPE) => MouseEventHandler<HTMLLIElement>;
  onSelectCouponTag: (hashtag: COUPON_HASHTAGS) => MouseEventHandler<HTMLLIElement>;
  onChangeCouponMessage: ChangeEventHandler<HTMLTextAreaElement>;
  onSubmitCreateForm: FormEventHandler<HTMLFormElement>;
}

const UnregisteredCouponCreateForm = (props: UnregisteredCouponCreateFormProps) => {
  const {
    currentCouponCount,
    currentCouponType,
    currentCouponTag,
    currentCouponMessage,
    onClickCouponCountUpdateButton,
    onSelectCouponType,
    onSelectCouponTag,
    onChangeCouponMessage,
    onSubmitCreateForm,
  } = props;

  usePreventReload();

  return (
    <Styled.FormRoot onSubmit={onSubmitCreateForm}>
      <Styled.CountContainer>
        <Input.Counter
          label='몇 명에게 주고 싶나요?'
          value={currentCouponCount}
          onClickPlusButton={onClickCouponCountUpdateButton(currentCouponCount + 1)}
          onClickMinusButton={onClickCouponCountUpdateButton(currentCouponCount - 1)}
        />
        <Link to={PATH.COUPON_CREATE} css={Styled.AnotherCouponCreatePageLink} replace>
          꼭꼭 회원에게 쿠폰 보내러가기
        </Link>
      </Styled.CountContainer>

      <SelectInput label='어떤 쿠폰인가요 ?'>
        {couponTypeCollection.map(({ engType }) => (
          <Styled.TypeOption
            key={engType}
            isSelected={engType === currentCouponType}
            onClick={onSelectCouponType(engType)}
          >
            <img src={THUMBNAIL[engType].src} alt={THUMBNAIL[engType].alt} width={50} height={50} />
          </Styled.TypeOption>
        ))}
      </SelectInput>

      <SelectInput.VerticalView label='당신의 마음을 선택해주세요 !'>
        {couponHashtags.map(hashtag => (
          <Styled.FeelOption
            key={hashtag}
            isSelected={hashtag === currentCouponTag}
            onClick={onSelectCouponTag(hashtag)}
          >
            #{hashtag}
          </Styled.FeelOption>
        ))}
      </SelectInput.VerticalView>

      <Styled.TextareaContainer>
        <label htmlFor='message-textarea'>메세지를 작성해보세요 (선택)</label>
        <Styled.MessageTextareaContainer>
          <Styled.MessageTextarea
            id='message-textarea'
            placeholder='시간, 장소 등 원하는 메시지를 보내보세요!'
            value={currentCouponMessage}
            onChange={onChangeCouponMessage}
          />
          <Styled.MessageLength>{currentCouponMessage.length} / 50</Styled.MessageLength>
        </Styled.MessageTextareaContainer>
      </Styled.TextareaContainer>

      <Styled.ButtonContainer>
        <Styled.ButtonInner>
          <Button>쿠폰 보내기</Button>
        </Styled.ButtonInner>
      </Styled.ButtonContainer>
    </Styled.FormRoot>
  );
};

export default UnregisteredCouponCreateForm;
