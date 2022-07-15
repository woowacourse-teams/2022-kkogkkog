import { css } from '@emotion/react';
import { ChangeEventHandler, FormEventHandler, useState } from 'react';

import Button from '@/@components/@shared/Button';
import Input from '@/@components/@shared/Input';
import Modal from '@/@components/@shared/Modal';
import SelectInput from '@/@components/@shared/SelectInput';
import UserSearchForm from '@/@components/user/UserSearchForm';
import { colors, couponTypes, modifiers } from '@/@pages/kkogkkog-list/create';
import { useModal } from '@/hooks/useModal';
import { KkogKkogType } from '@/types/domain';

import * as Styled from './style';

interface KkogKkogCreateFormProps {
  currentReceiverList: any[];
  currentType: KkogKkogType;
  currentModifier: '재미있게' | '활기차게' | '한턱쏘는';
  currentColor: typeof colors[number];
  currentMessage: string;
  onSelectReceiver: ({ id: number }) => void;
  onSelectType: (type: KkogKkogType) => void;
  onSelectModifier: (modifier: '재미있게' | '활기차게' | '한턱쏘는') => void;
  onSelectColor: (color: typeof colors[number]) => void;
  onChangeMessage: ChangeEventHandler<HTMLInputElement>;
  onSubmitCreateForm: FormEventHandler<HTMLFormElement>;
}

const KkogKkogCreateForm = (props: KkogKkogCreateFormProps) => {
  const {
    currentReceiverList,
    currentType,
    currentModifier,
    currentColor,
    currentMessage,
    onSelectReceiver,
    onSelectType,
    onSelectModifier,
    onSelectColor,
    onChangeMessage,
    onSubmitCreateForm,
  } = props;

  const { isShowModal, openModal, closeModal } = useModal();

  return (
    <Styled.FormRoot onSubmit={onSubmitCreateForm}>
      <Styled.FindUserContainer>
        <div>누구에게 보내시나요?</div>
        <div onClick={openModal}>🔍 유저를 찾아보세요</div>
      </Styled.FindUserContainer>

      {isShowModal && (
        <Modal onCloseModal={closeModal} position='bottom'>
          <UserSearchForm
            currentReceiverList={currentReceiverList}
            onSelectReceiver={onSelectReceiver}
          />
        </Modal>
      )}

      <SelectInput label='어떤 쿠폰인가요?'>
        {couponTypes.map(({ type, imageURL }) => (
          <Styled.TypeOption
            key={type}
            isSelected={type === currentType}
            onClick={() => onSelectType(type)}
          >
            <img src={imageURL} alt='hi' />
          </Styled.TypeOption>
        ))}
      </SelectInput>

      <SelectInput label='당신의 기분을 골라주세요'>
        {modifiers.map(modifier => (
          <Styled.FeelOption
            key={modifier}
            isSelected={modifier === currentModifier}
            onClick={() => onSelectModifier(modifier)}
          >
            #{modifier}
          </Styled.FeelOption>
        ))}
      </SelectInput>

      <SelectInput label='쿠폰의 색상을 골라주세요'>
        {colors.map(color => (
          <Styled.ColorOption
            key={color}
            color={color}
            isSelected={color === currentColor}
            onClick={() => onSelectColor(color)}
          />
        ))}
      </SelectInput>

      <Input
        label='하고 싶은 말을 적어주세요'
        placeholder='쿠폰을 사용하는 사람을 생각하며 적어주세요!'
        value={currentMessage}
        onChange={onChangeMessage}
      />

      <Styled.ButtonContainer>
        <Styled.ButtonInner>
          <Button>꼭꼭 발급하기</Button>
        </Styled.ButtonInner>
      </Styled.ButtonContainer>
    </Styled.FormRoot>
  );
};

export default KkogKkogCreateForm;
