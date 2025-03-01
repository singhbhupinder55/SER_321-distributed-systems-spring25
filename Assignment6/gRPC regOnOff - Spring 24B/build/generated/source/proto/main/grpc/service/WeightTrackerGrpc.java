package service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.49.1)",
    comments = "Source: services/weightTracker.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class WeightTrackerGrpc {

  private WeightTrackerGrpc() {}

  public static final String SERVICE_NAME = "services.WeightTracker";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<service.AddWeightRequest,
      service.AddWeightResponse> getAddWeightMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "addWeight",
      requestType = service.AddWeightRequest.class,
      responseType = service.AddWeightResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.AddWeightRequest,
      service.AddWeightResponse> getAddWeightMethod() {
    io.grpc.MethodDescriptor<service.AddWeightRequest, service.AddWeightResponse> getAddWeightMethod;
    if ((getAddWeightMethod = WeightTrackerGrpc.getAddWeightMethod) == null) {
      synchronized (WeightTrackerGrpc.class) {
        if ((getAddWeightMethod = WeightTrackerGrpc.getAddWeightMethod) == null) {
          WeightTrackerGrpc.getAddWeightMethod = getAddWeightMethod =
              io.grpc.MethodDescriptor.<service.AddWeightRequest, service.AddWeightResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "addWeight"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.AddWeightRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.AddWeightResponse.getDefaultInstance()))
              .setSchemaDescriptor(new WeightTrackerMethodDescriptorSupplier("addWeight"))
              .build();
        }
      }
    }
    return getAddWeightMethod;
  }

  private static volatile io.grpc.MethodDescriptor<service.GetWeightRequest,
      service.GetWeightResponse> getGetWeightMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getWeight",
      requestType = service.GetWeightRequest.class,
      responseType = service.GetWeightResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.GetWeightRequest,
      service.GetWeightResponse> getGetWeightMethod() {
    io.grpc.MethodDescriptor<service.GetWeightRequest, service.GetWeightResponse> getGetWeightMethod;
    if ((getGetWeightMethod = WeightTrackerGrpc.getGetWeightMethod) == null) {
      synchronized (WeightTrackerGrpc.class) {
        if ((getGetWeightMethod = WeightTrackerGrpc.getGetWeightMethod) == null) {
          WeightTrackerGrpc.getGetWeightMethod = getGetWeightMethod =
              io.grpc.MethodDescriptor.<service.GetWeightRequest, service.GetWeightResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getWeight"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.GetWeightRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.GetWeightResponse.getDefaultInstance()))
              .setSchemaDescriptor(new WeightTrackerMethodDescriptorSupplier("getWeight"))
              .build();
        }
      }
    }
    return getGetWeightMethod;
  }

  private static volatile io.grpc.MethodDescriptor<service.BMIRequest,
      service.BMIResponse> getGetBMIMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getBMI",
      requestType = service.BMIRequest.class,
      responseType = service.BMIResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.BMIRequest,
      service.BMIResponse> getGetBMIMethod() {
    io.grpc.MethodDescriptor<service.BMIRequest, service.BMIResponse> getGetBMIMethod;
    if ((getGetBMIMethod = WeightTrackerGrpc.getGetBMIMethod) == null) {
      synchronized (WeightTrackerGrpc.class) {
        if ((getGetBMIMethod = WeightTrackerGrpc.getGetBMIMethod) == null) {
          WeightTrackerGrpc.getGetBMIMethod = getGetBMIMethod =
              io.grpc.MethodDescriptor.<service.BMIRequest, service.BMIResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getBMI"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.BMIRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.BMIResponse.getDefaultInstance()))
              .setSchemaDescriptor(new WeightTrackerMethodDescriptorSupplier("getBMI"))
              .build();
        }
      }
    }
    return getGetBMIMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static WeightTrackerStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WeightTrackerStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WeightTrackerStub>() {
        @java.lang.Override
        public WeightTrackerStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WeightTrackerStub(channel, callOptions);
        }
      };
    return WeightTrackerStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static WeightTrackerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WeightTrackerBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WeightTrackerBlockingStub>() {
        @java.lang.Override
        public WeightTrackerBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WeightTrackerBlockingStub(channel, callOptions);
        }
      };
    return WeightTrackerBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static WeightTrackerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WeightTrackerFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WeightTrackerFutureStub>() {
        @java.lang.Override
        public WeightTrackerFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WeightTrackerFutureStub(channel, callOptions);
        }
      };
    return WeightTrackerFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class WeightTrackerImplBase implements io.grpc.BindableService {

    /**
     */
    public void addWeight(service.AddWeightRequest request,
        io.grpc.stub.StreamObserver<service.AddWeightResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddWeightMethod(), responseObserver);
    }

    /**
     */
    public void getWeight(service.GetWeightRequest request,
        io.grpc.stub.StreamObserver<service.GetWeightResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetWeightMethod(), responseObserver);
    }

    /**
     */
    public void getBMI(service.BMIRequest request,
        io.grpc.stub.StreamObserver<service.BMIResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetBMIMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAddWeightMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                service.AddWeightRequest,
                service.AddWeightResponse>(
                  this, METHODID_ADD_WEIGHT)))
          .addMethod(
            getGetWeightMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                service.GetWeightRequest,
                service.GetWeightResponse>(
                  this, METHODID_GET_WEIGHT)))
          .addMethod(
            getGetBMIMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                service.BMIRequest,
                service.BMIResponse>(
                  this, METHODID_GET_BMI)))
          .build();
    }
  }

  /**
   */
  public static final class WeightTrackerStub extends io.grpc.stub.AbstractAsyncStub<WeightTrackerStub> {
    private WeightTrackerStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WeightTrackerStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WeightTrackerStub(channel, callOptions);
    }

    /**
     */
    public void addWeight(service.AddWeightRequest request,
        io.grpc.stub.StreamObserver<service.AddWeightResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddWeightMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getWeight(service.GetWeightRequest request,
        io.grpc.stub.StreamObserver<service.GetWeightResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetWeightMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getBMI(service.BMIRequest request,
        io.grpc.stub.StreamObserver<service.BMIResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetBMIMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class WeightTrackerBlockingStub extends io.grpc.stub.AbstractBlockingStub<WeightTrackerBlockingStub> {
    private WeightTrackerBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WeightTrackerBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WeightTrackerBlockingStub(channel, callOptions);
    }

    /**
     */
    public service.AddWeightResponse addWeight(service.AddWeightRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddWeightMethod(), getCallOptions(), request);
    }

    /**
     */
    public service.GetWeightResponse getWeight(service.GetWeightRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetWeightMethod(), getCallOptions(), request);
    }

    /**
     */
    public service.BMIResponse getBMI(service.BMIRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetBMIMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class WeightTrackerFutureStub extends io.grpc.stub.AbstractFutureStub<WeightTrackerFutureStub> {
    private WeightTrackerFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WeightTrackerFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WeightTrackerFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.AddWeightResponse> addWeight(
        service.AddWeightRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddWeightMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.GetWeightResponse> getWeight(
        service.GetWeightRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetWeightMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.BMIResponse> getBMI(
        service.BMIRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetBMIMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD_WEIGHT = 0;
  private static final int METHODID_GET_WEIGHT = 1;
  private static final int METHODID_GET_BMI = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final WeightTrackerImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(WeightTrackerImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD_WEIGHT:
          serviceImpl.addWeight((service.AddWeightRequest) request,
              (io.grpc.stub.StreamObserver<service.AddWeightResponse>) responseObserver);
          break;
        case METHODID_GET_WEIGHT:
          serviceImpl.getWeight((service.GetWeightRequest) request,
              (io.grpc.stub.StreamObserver<service.GetWeightResponse>) responseObserver);
          break;
        case METHODID_GET_BMI:
          serviceImpl.getBMI((service.BMIRequest) request,
              (io.grpc.stub.StreamObserver<service.BMIResponse>) responseObserver);
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

  private static abstract class WeightTrackerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    WeightTrackerBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return service.WeightTrackerProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("WeightTracker");
    }
  }

  private static final class WeightTrackerFileDescriptorSupplier
      extends WeightTrackerBaseDescriptorSupplier {
    WeightTrackerFileDescriptorSupplier() {}
  }

  private static final class WeightTrackerMethodDescriptorSupplier
      extends WeightTrackerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    WeightTrackerMethodDescriptorSupplier(String methodName) {
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
      synchronized (WeightTrackerGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new WeightTrackerFileDescriptorSupplier())
              .addMethod(getAddWeightMethod())
              .addMethod(getGetWeightMethod())
              .addMethod(getGetBMIMethod())
              .build();
        }
      }
    }
    return result;
  }
}
