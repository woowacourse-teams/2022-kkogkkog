import { ChangeEventHandler, FormEventHandler } from 'react';

import Button from '@/@components/@shared/Button';
import Input from '@/@components/@shared/Input';
import Modal from '@/@components/@shared/Modal';
import SelectInput from '@/@components/@shared/SelectInput';
import UserSearchForm from '@/@components/user/UserSearchForm';
import { useModal } from '@/@hooks/@common/useModal';
import {
  KKOGKKOG_COLORS,
  kkogkkog_colors,
  KKOGKKOG_KOREAN_TYPE,
  KKOGKKOG_MODIFIERS,
  kkogkkog_modifiers,
  kkogkkog_type,
  User,
} from '@/types/domain';

import * as Styled from './style';

interface KkogKkogCreateFormProps {
  currentReceiverList: any[];
  currentType: KKOGKKOG_KOREAN_TYPE;
  currentModifier: KKOGKKOG_MODIFIERS;
  currentColor: KKOGKKOG_COLORS;
  currentMessage: string;
  onSelectReceiver: (user: User) => void;
  onSelectType: (type: KKOGKKOG_KOREAN_TYPE) => void;
  onSelectModifier: (modifier: KKOGKKOG_MODIFIERS) => void;
  onSelectColor: (color: KKOGKKOG_COLORS) => void;
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
        {kkogkkog_type.map(({ koreanType, imageUrl }) => (
          <Styled.TypeOption
            key={koreanType}
            isSelected={koreanType === currentType}
            onClick={() => onSelectType(koreanType)}
          >
            <img src={imageUrl} alt='hi' />
          </Styled.TypeOption>
        ))}
      </SelectInput>

      <SelectInput label='당신의 기분을 골라주세요'>
        {kkogkkog_modifiers.map(modifier => (
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
        {kkogkkog_colors.map(color => (
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
