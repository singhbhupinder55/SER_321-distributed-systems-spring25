package service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.49.1)",
    comments = "Source: services/follow.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class FollowGrpc {

  private FollowGrpc() {}

  public static final String SERVICE_NAME = "services.Follow";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<service.UserReq,
      service.UserRes> getAddUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "addUser",
      requestType = service.UserReq.class,
      responseType = service.UserRes.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.UserReq,
      service.UserRes> getAddUserMethod() {
    io.grpc.MethodDescriptor<service.UserReq, service.UserRes> getAddUserMethod;
    if ((getAddUserMethod = FollowGrpc.getAddUserMethod) == null) {
      synchronized (FollowGrpc.class) {
        if ((getAddUserMethod = FollowGrpc.getAddUserMethod) == null) {
          FollowGrpc.getAddUserMethod = getAddUserMethod =
              io.grpc.MethodDescriptor.<service.UserReq, service.UserRes>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "addUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.UserReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.UserRes.getDefaultInstance()))
              .setSchemaDescriptor(new FollowMethodDescriptorSupplier("addUser"))
              .build();
        }
      }
    }
    return getAddUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<service.UserReq,
      service.UserRes> getFollowMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "follow",
      requestType = service.UserReq.class,
      responseType = service.UserRes.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.UserReq,
      service.UserRes> getFollowMethod() {
    io.grpc.MethodDescriptor<service.UserReq, service.UserRes> getFollowMethod;
    if ((getFollowMethod = FollowGrpc.getFollowMethod) == null) {
      synchronized (FollowGrpc.class) {
        if ((getFollowMethod = FollowGrpc.getFollowMethod) == null) {
          FollowGrpc.getFollowMethod = getFollowMethod =
              io.grpc.MethodDescriptor.<service.UserReq, service.UserRes>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "follow"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.UserReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.UserRes.getDefaultInstance()))
              .setSchemaDescriptor(new FollowMethodDescriptorSupplier("follow"))
              .build();
        }
      }
    }
    return getFollowMethod;
  }

  private static volatile io.grpc.MethodDescriptor<service.UserReq,
      service.UserRes> getViewFollowingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "viewFollowing",
      requestType = service.UserReq.class,
      responseType = service.UserRes.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.UserReq,
      service.UserRes> getViewFollowingMethod() {
    io.grpc.MethodDescriptor<service.UserReq, service.UserRes> getViewFollowingMethod;
    if ((getViewFollowingMethod = FollowGrpc.getViewFollowingMethod) == null) {
      synchronized (FollowGrpc.class) {
        if ((getViewFollowingMethod = FollowGrpc.getViewFollowingMethod) == null) {
          FollowGrpc.getViewFollowingMethod = getViewFollowingMethod =
              io.grpc.MethodDescriptor.<service.UserReq, service.UserRes>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "viewFollowing"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.UserReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.UserRes.getDefaultInstance()))
              .setSchemaDescriptor(new FollowMethodDescriptorSupplier("viewFollowing"))
              .build();
        }
      }
    }
    return getViewFollowingMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FollowStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FollowStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FollowStub>() {
        @java.lang.Override
        public FollowStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FollowStub(channel, callOptions);
        }
      };
    return FollowStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FollowBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FollowBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FollowBlockingStub>() {
        @java.lang.Override
        public FollowBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FollowBlockingStub(channel, callOptions);
        }
      };
    return FollowBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FollowFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FollowFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FollowFutureStub>() {
        @java.lang.Override
        public FollowFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FollowFutureStub(channel, callOptions);
        }
      };
    return FollowFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class FollowImplBase implements io.grpc.BindableService {

    /**
     */
    public void addUser(service.UserReq request,
        io.grpc.stub.StreamObserver<service.UserRes> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddUserMethod(), responseObserver);
    }

    /**
     */
    public void follow(service.UserReq request,
        io.grpc.stub.StreamObserver<service.UserRes> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFollowMethod(), responseObserver);
    }

    /**
     */
    public void viewFollowing(service.UserReq request,
        io.grpc.stub.StreamObserver<service.UserRes> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getViewFollowingMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAddUserMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                service.UserReq,
                service.UserRes>(
                  this, METHODID_ADD_USER)))
          .addMethod(
            getFollowMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                service.UserReq,
                service.UserRes>(
                  this, METHODID_FOLLOW)))
          .addMethod(
            getViewFollowingMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                service.UserReq,
                service.UserRes>(
                  this, METHODID_VIEW_FOLLOWING)))
          .build();
    }
  }

  /**
   */
  public static final class FollowStub extends io.grpc.stub.AbstractAsyncStub<FollowStub> {
    private FollowStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FollowStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FollowStub(channel, callOptions);
    }

    /**
     */
    public void addUser(service.UserReq request,
        io.grpc.stub.StreamObserver<service.UserRes> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void follow(service.UserReq request,
        io.grpc.stub.StreamObserver<service.UserRes> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFollowMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void viewFollowing(service.UserReq request,
        io.grpc.stub.StreamObserver<service.UserRes> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getViewFollowingMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class FollowBlockingStub extends io.grpc.stub.AbstractBlockingStub<FollowBlockingStub> {
    private FollowBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FollowBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FollowBlockingStub(channel, callOptions);
    }

    /**
     */
    public service.UserRes addUser(service.UserReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public service.UserRes follow(service.UserReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFollowMethod(), getCallOptions(), request);
    }

    /**
     */
    public service.UserRes viewFollowing(service.UserReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getViewFollowingMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class FollowFutureStub extends io.grpc.stub.AbstractFutureStub<FollowFutureStub> {
    private FollowFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FollowFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FollowFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.UserRes> addUser(
        service.UserReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.UserRes> follow(
        service.UserReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFollowMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.UserRes> viewFollowing(
        service.UserReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getViewFollowingMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD_USER = 0;
  private static final int METHODID_FOLLOW = 1;
  private static final int METHODID_VIEW_FOLLOWING = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final FollowImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(FollowImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD_USER:
          serviceImpl.addUser((service.UserReq) request,
              (io.grpc.stub.StreamObserver<service.UserRes>) responseObserver);
          break;
        case METHODID_FOLLOW:
          serviceImpl.follow((service.UserReq) request,
              (io.grpc.stub.StreamObserver<service.UserRes>) responseObserver);
          break;
        case METHODID_VIEW_FOLLOWING:
          serviceImpl.viewFollowing((service.UserReq) request,
              (io.grpc.stub.StreamObserver<service.UserRes>) responseObserver);
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

  private static abstract class FollowBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FollowBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return service.FollowProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Follow");
    }
  }

  private static final class FollowFileDescriptorSupplier
      extends FollowBaseDescriptorSupplier {
    FollowFileDescriptorSupplier() {}
  }

  private static final class FollowMethodDescriptorSupplier
      extends FollowBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    FollowMethodDescriptorSupplier(String methodName) {
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
      synchronized (FollowGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FollowFileDescriptorSupplier())
              .addMethod(getAddUserMethod())
              .addMethod(getFollowMethod())
              .addMethod(getViewFollowingMethod())
              .build();
        }
      }
    }
    return result;
  }
}
