package service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.49.1)",
    comments = "Source: services/flowers.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class FlowersGrpc {

  private FlowersGrpc() {}

  public static final String SERVICE_NAME = "services.Flowers";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<service.FlowerReq,
      service.FlowerRes> getPlantFlowerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "plantFlower",
      requestType = service.FlowerReq.class,
      responseType = service.FlowerRes.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.FlowerReq,
      service.FlowerRes> getPlantFlowerMethod() {
    io.grpc.MethodDescriptor<service.FlowerReq, service.FlowerRes> getPlantFlowerMethod;
    if ((getPlantFlowerMethod = FlowersGrpc.getPlantFlowerMethod) == null) {
      synchronized (FlowersGrpc.class) {
        if ((getPlantFlowerMethod = FlowersGrpc.getPlantFlowerMethod) == null) {
          FlowersGrpc.getPlantFlowerMethod = getPlantFlowerMethod =
              io.grpc.MethodDescriptor.<service.FlowerReq, service.FlowerRes>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "plantFlower"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.FlowerReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.FlowerRes.getDefaultInstance()))
              .setSchemaDescriptor(new FlowersMethodDescriptorSupplier("plantFlower"))
              .build();
        }
      }
    }
    return getPlantFlowerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      service.FlowerViewRes> getViewFlowersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "viewFlowers",
      requestType = com.google.protobuf.Empty.class,
      responseType = service.FlowerViewRes.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      service.FlowerViewRes> getViewFlowersMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, service.FlowerViewRes> getViewFlowersMethod;
    if ((getViewFlowersMethod = FlowersGrpc.getViewFlowersMethod) == null) {
      synchronized (FlowersGrpc.class) {
        if ((getViewFlowersMethod = FlowersGrpc.getViewFlowersMethod) == null) {
          FlowersGrpc.getViewFlowersMethod = getViewFlowersMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, service.FlowerViewRes>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "viewFlowers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.FlowerViewRes.getDefaultInstance()))
              .setSchemaDescriptor(new FlowersMethodDescriptorSupplier("viewFlowers"))
              .build();
        }
      }
    }
    return getViewFlowersMethod;
  }

  private static volatile io.grpc.MethodDescriptor<service.FlowerCare,
      service.WaterRes> getWaterFlowerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "waterFlower",
      requestType = service.FlowerCare.class,
      responseType = service.WaterRes.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.FlowerCare,
      service.WaterRes> getWaterFlowerMethod() {
    io.grpc.MethodDescriptor<service.FlowerCare, service.WaterRes> getWaterFlowerMethod;
    if ((getWaterFlowerMethod = FlowersGrpc.getWaterFlowerMethod) == null) {
      synchronized (FlowersGrpc.class) {
        if ((getWaterFlowerMethod = FlowersGrpc.getWaterFlowerMethod) == null) {
          FlowersGrpc.getWaterFlowerMethod = getWaterFlowerMethod =
              io.grpc.MethodDescriptor.<service.FlowerCare, service.WaterRes>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "waterFlower"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.FlowerCare.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.WaterRes.getDefaultInstance()))
              .setSchemaDescriptor(new FlowersMethodDescriptorSupplier("waterFlower"))
              .build();
        }
      }
    }
    return getWaterFlowerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<service.FlowerCare,
      service.CareRes> getCareForFlowerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "careForFlower",
      requestType = service.FlowerCare.class,
      responseType = service.CareRes.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.FlowerCare,
      service.CareRes> getCareForFlowerMethod() {
    io.grpc.MethodDescriptor<service.FlowerCare, service.CareRes> getCareForFlowerMethod;
    if ((getCareForFlowerMethod = FlowersGrpc.getCareForFlowerMethod) == null) {
      synchronized (FlowersGrpc.class) {
        if ((getCareForFlowerMethod = FlowersGrpc.getCareForFlowerMethod) == null) {
          FlowersGrpc.getCareForFlowerMethod = getCareForFlowerMethod =
              io.grpc.MethodDescriptor.<service.FlowerCare, service.CareRes>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "careForFlower"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.FlowerCare.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.CareRes.getDefaultInstance()))
              .setSchemaDescriptor(new FlowersMethodDescriptorSupplier("careForFlower"))
              .build();
        }
      }
    }
    return getCareForFlowerMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FlowersStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FlowersStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FlowersStub>() {
        @java.lang.Override
        public FlowersStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FlowersStub(channel, callOptions);
        }
      };
    return FlowersStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FlowersBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FlowersBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FlowersBlockingStub>() {
        @java.lang.Override
        public FlowersBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FlowersBlockingStub(channel, callOptions);
        }
      };
    return FlowersBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FlowersFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FlowersFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FlowersFutureStub>() {
        @java.lang.Override
        public FlowersFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FlowersFutureStub(channel, callOptions);
        }
      };
    return FlowersFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class FlowersImplBase implements io.grpc.BindableService {

    /**
     */
    public void plantFlower(service.FlowerReq request,
        io.grpc.stub.StreamObserver<service.FlowerRes> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPlantFlowerMethod(), responseObserver);
    }

    /**
     */
    public void viewFlowers(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<service.FlowerViewRes> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getViewFlowersMethod(), responseObserver);
    }

    /**
     */
    public void waterFlower(service.FlowerCare request,
        io.grpc.stub.StreamObserver<service.WaterRes> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getWaterFlowerMethod(), responseObserver);
    }

    /**
     */
    public void careForFlower(service.FlowerCare request,
        io.grpc.stub.StreamObserver<service.CareRes> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCareForFlowerMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getPlantFlowerMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                service.FlowerReq,
                service.FlowerRes>(
                  this, METHODID_PLANT_FLOWER)))
          .addMethod(
            getViewFlowersMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                service.FlowerViewRes>(
                  this, METHODID_VIEW_FLOWERS)))
          .addMethod(
            getWaterFlowerMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                service.FlowerCare,
                service.WaterRes>(
                  this, METHODID_WATER_FLOWER)))
          .addMethod(
            getCareForFlowerMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                service.FlowerCare,
                service.CareRes>(
                  this, METHODID_CARE_FOR_FLOWER)))
          .build();
    }
  }

  /**
   */
  public static final class FlowersStub extends io.grpc.stub.AbstractAsyncStub<FlowersStub> {
    private FlowersStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FlowersStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FlowersStub(channel, callOptions);
    }

    /**
     */
    public void plantFlower(service.FlowerReq request,
        io.grpc.stub.StreamObserver<service.FlowerRes> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPlantFlowerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void viewFlowers(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<service.FlowerViewRes> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getViewFlowersMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void waterFlower(service.FlowerCare request,
        io.grpc.stub.StreamObserver<service.WaterRes> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getWaterFlowerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void careForFlower(service.FlowerCare request,
        io.grpc.stub.StreamObserver<service.CareRes> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCareForFlowerMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class FlowersBlockingStub extends io.grpc.stub.AbstractBlockingStub<FlowersBlockingStub> {
    private FlowersBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FlowersBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FlowersBlockingStub(channel, callOptions);
    }

    /**
     */
    public service.FlowerRes plantFlower(service.FlowerReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPlantFlowerMethod(), getCallOptions(), request);
    }

    /**
     */
    public service.FlowerViewRes viewFlowers(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getViewFlowersMethod(), getCallOptions(), request);
    }

    /**
     */
    public service.WaterRes waterFlower(service.FlowerCare request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getWaterFlowerMethod(), getCallOptions(), request);
    }

    /**
     */
    public service.CareRes careForFlower(service.FlowerCare request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCareForFlowerMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class FlowersFutureStub extends io.grpc.stub.AbstractFutureStub<FlowersFutureStub> {
    private FlowersFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FlowersFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FlowersFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.FlowerRes> plantFlower(
        service.FlowerReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPlantFlowerMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.FlowerViewRes> viewFlowers(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getViewFlowersMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.WaterRes> waterFlower(
        service.FlowerCare request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getWaterFlowerMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.CareRes> careForFlower(
        service.FlowerCare request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCareForFlowerMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PLANT_FLOWER = 0;
  private static final int METHODID_VIEW_FLOWERS = 1;
  private static final int METHODID_WATER_FLOWER = 2;
  private static final int METHODID_CARE_FOR_FLOWER = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final FlowersImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(FlowersImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PLANT_FLOWER:
          serviceImpl.plantFlower((service.FlowerReq) request,
              (io.grpc.stub.StreamObserver<service.FlowerRes>) responseObserver);
          break;
        case METHODID_VIEW_FLOWERS:
          serviceImpl.viewFlowers((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<service.FlowerViewRes>) responseObserver);
          break;
        case METHODID_WATER_FLOWER:
          serviceImpl.waterFlower((service.FlowerCare) request,
              (io.grpc.stub.StreamObserver<service.WaterRes>) responseObserver);
          break;
        case METHODID_CARE_FOR_FLOWER:
          serviceImpl.careForFlower((service.FlowerCare) request,
              (io.grpc.stub.StreamObserver<service.CareRes>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class FlowersBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FlowersBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return service.FlowerProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Flowers");
    }
  }

  private static final class FlowersFileDescriptorSupplier
      extends FlowersBaseDescriptorSupplier {
    FlowersFileDescriptorSupplier() {}
  }

  private static final class FlowersMethodDescriptorSupplier
      extends FlowersBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    FlowersMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (FlowersGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FlowersFileDescriptorSupplier())
              .addMethod(getPlantFlowerMethod())
              .addMethod(getViewFlowersMethod())
              .addMethod(getWaterFlowerMethod())
              .addMethod(getCareForFlowerMethod())
              .build();
        }
      }
    }
    return result;
  }
}
