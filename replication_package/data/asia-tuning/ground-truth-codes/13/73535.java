@Override
 protected Builder createRestAdapterBuilder() {
    Builder builder = super.createRestAdapterBuilder();
    builder.setClient(new CustomClient());
    return builder;
  };
