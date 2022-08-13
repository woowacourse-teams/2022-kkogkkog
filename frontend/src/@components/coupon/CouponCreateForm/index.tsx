import { ChangeEventHandler, FormEventHandler, useEffect } from 'react';

import Button from '@/@components/@shared/Button';
import Input from '@/@components/@shared/Input';
import SelectInput from '@/@components/@shared/SelectInput';
import UserSearchModal from '@/@components/user/UserSearchModal';
import { useModal } from '@/@hooks/@common/useModal';
import { usePreventReload } from '@/@hooks/@common/usePreventReload';
import {
  COUPON_COLORS,
  COUPON_ENG_TYPE,
  COUPON_MODIFIERS,
  couponModifiers,
  couponTypeCollection,
  THUMBNAIL,
} from '@/types/client/coupon';
import { UserResponse } from '@/types/remote/response';

import * as Styled from './style';

interface CouponCreateFormProps {
  currentReceiverList: UserResponse[];
  currentType: COUPON_ENG_TYPE;
  currentModifier: COUPON_MODIFIERS;
  currentColor: COUPON_COLORS;
  currentMessage: string;
  onSelectReceiver: (user: UserResponse) => void;
  onSelectType: (type: COUPON_ENG_TYPE) => void;
  onSelectModifier: (modifier: COUPON_MODIFIERS) => void;
  onSelectColor: (color: COUPON_COLORS) => void;
  onChangeMessage: ChangeEventHandler<HTMLInputElement>;
  onSubmitCreateForm: FormEventHandler<HTMLFormElement>;
}

const CouponCreateForm = (props: CouponCreateFormProps) => {
  const {
    currentReceiverList,
    currentType,
    currentModifier,
    currentMessage,
    onSelectReceiver,
    onSelectType,
    onSelectModifier,
    onChangeMessage,
    onSubmitCreateForm,
  } = props;

  usePreventReload();

  const { isShowModal, openModal, closeModal } = useModal();

  return (
    <Styled.FormRoot onSubmit={onSubmitCreateForm}>
      <Styled.FindUserContainer>
        <div>누구에게 보내시나요?</div>
        <Styled.FindUserInput onClick={openModal}>
          {currentReceiverList.length === 0 && <span>유저를 찾아보세요</span>}

          {currentReceiverList.length !== 0 && (
            <Styled.SelectedUserListContainer>
              {currentReceiverList.map(receiver => (
                <Styled.SelectedUserContainer key={receiver.id}>
                  <span>{receiver.nickname}</span>
                </Styled.SelectedUserContainer>
              ))}
            </Styled.SelectedUserListContainer>
          )}

          <span>🔍</span>
        </Styled.FindUserInput>
      </Styled.FindUserContainer>

      {isShowModal && (
        <UserSearchModal
          currentReceiverList={currentReceiverList}
          onSelectReceiver={onSelectReceiver}
          closeModal={closeModal}
        />
      )}

      <SelectInput label='어떤 쿠폰인가요?'>
        {couponTypeCollection.map(({ engType }) => (
          <Styled.TypeOption
            key={engType}
            isSelected={engType === currentType}
            onClick={() => onSelectType(engType)}
          >
            <img src={THUMBNAIL[engType]} alt='hi' />
          </Styled.TypeOption>
        ))}
      </SelectInput>

      <SelectInput label='당신의 기분을 골라주세요'>
        {couponModifiers.map(modifier => (
          <Styled.FeelOption
            key={modifier}
            isSelected={modifier === currentModifier}
            onClick={() => onSelectModifier(modifier)}
          >
            #{modifier}
          </Styled.FeelOption>
        ))}
      </SelectInput>

      <Input
        label='하고 싶은 말을 적어주세요'
        placeholder='50자 이하로 작성해주세요'
        value={currentMessage}
        onChange={onChangeMessage}
        maxLength={50}
      />

      <Styled.ButtonContainer>
        <Styled.ButtonInner>
          <Button>쿠폰 보내기</Button>
        </Styled.ButtonInner>
      </Styled.ButtonContainer>
    </Styled.FormRoot>
  );
};

export default CouponCreateForm;
