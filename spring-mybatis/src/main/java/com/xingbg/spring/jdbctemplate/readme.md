# 1.事务三大接口

1. PlatformTransactionManager 事务管理器
2. TransactionDefinition 事务的一些基础信息，如超时时间、隔离级别、传播属性等
3. TransactionStatus 事务的一些状态信息，如是否一个新的事务、是否已被标记为回滚

## 2.PlatformTransactionManager

```
public interface PlatformTransactionManager {

    //根据事务定义TransactionDefinition，获取事务
    TransactionStatus getTransaction(TransactionDefinition definition);

    //提交事务
    void commit(TransactionStatus status);

    //回滚事务
    void rollback(TransactionStatus status);
}
```

### 2.1 事务定义接口TransactionDefinition

事务的定义包括: 事务的隔离级别，事务的传播属性，超时时间设置，是否只读

事务的隔离级别是数据库本身的事务功能，事务的传播属性则是spring为我们提供的功能

该接口的实现DefaultTransactionDefinition,默认的事务定义

```
public class DefaultTransactionDefinition implements TransactionDefinition, Serializable {

    private int propagationBehavior = PROPAGATION_REQUIRED;

    private int isolationLevel = ISOLATION_DEFAULT;

    private int timeout = TIMEOUT_DEFAULT;

    private boolean readOnly = false;

    //略
}
```

1.事务的传播属性为PROPAGATION_REQUIRED，即当前没有事务的时候，创建一个，如果有则使用当前事务 2.事务的隔离级别采用底层数据库默认的隔离级别 3.超时时间采用底层数据库默认的超时时间 4.是否只读为false

### 2.2 事务接口定义 TransactionStatus

TransactionStatus它继承了SavepointManager接口，SavepointManager是对事务中上述保存点功能的封装，如下：

```
public interface SavepointManager {

    Object createSavepoint() throws TransactionException;

    void rollbackToSavepoint(Object savepoint) throws TransactionException;

    void releaseSavepoint(Object savepoint) throws TransactionException;

}
```

TransactionStatus本身更多存储的是事务的一些状态信息



是否是一个新的事物 是否有保存点 是否已被标记为回滚

整个流程:

```
@Autowired
private PlatformTransactionManager transactionManager;

TransactionStatus status = null;

// 手动开启事务
status = transactionManager.getTransaction(new DefaultTransactionDefinition());

// 事务提交
transactionManager.commit(status);

// 事务回滚
if (StringMoreUtils.checkValNotNull(status)) {

    transactionManager.rollback(status);
}
```

参考链接: https://my.oschina.net/pingpangkuangmo/blog/415162

## 3. 事务的基本要素 ACID

原子性: 事务开始后 要么全部做完，要么全部不做，不能执行一半停止，如果在执行中出错，会回滚到事务开始前的状态，也就是说事务是一个不可分割的整体，就好像化学里的原子，是构成物质的基本单位

一致性: A像B转账，不可能A扣了钱，B没有收到

隔离性: 同一时间，只允许一个事务对数据操作，不同事务间没有干扰，比如A从一张银行卡取钱，在这个动作结束前，B不能向银行卡存钱

持久性: 事务完成后，事务对数据库的所有更新将被保存到数据库，不能回滚

## 4. 事务的并发问题

1. 脏读: 事务A读取了事务B更新的数据，然后事务B回滚，那么事务A读取的数据为脏数据
2. 不可重复读: 事务A多次读取同一数据,事务B在事务A多次读取的过程中，对数据操作并提交，导致事务A读取到的数据不一致
3. 幻读: 系统管理员A将学生成绩从具体分数划分为ABCDE等级，但是这个时候系统管理B又插入了一条具体分数的记录，当A操作结束后发现还有一条记录没有改过来，就好像发生了幻觉

不可重复读侧重于修改，幻读侧重于新增或者删除操作，解决不可重复读需要锁住满足条件的行，解决幻读是锁表

## 5. 事务管理方式

spring支持编程式事务管理和声明式事务管理两种方式。

编程式事务管理使用TransactionTemplate或者直接使用底层的PlatformTransactionManager。对于编程式事务管理，spring推荐使用TransactionTemplate。

声明式事务管理建立在AOP之上的。其本质是对方法前后进行拦截，然后在目标方法开始之前创建或者加入一个事务，在执行完目标方法之后根据执行情况提交或者回滚事务。声明式事务最大的优点就是不需要通过编程的方式管理事务，这样就不需要在业务逻辑代码中掺杂事务管理的代码，只需在配置文件中做相关的事务规则声明(或通过基于@Transactional注解的方式)，便可以将事务规则应用到业务逻辑中。

显然声明式事务管理要优于编程式事务管理，这正是spring倡导的非侵入式的开发方式。声明式事务管理使业务代码不受污染，一个普通的POJO对象，只要加上注解就可以获得完全的事务支持。和编程式事务相比，声明式事务唯一不足地方是，后者的最细粒度只能作用到方法级别，无法做到像编程式事务那样可以作用到代码块级别。但是即便有这样的需求，也存在很多变通的方法，比如，可以将需要进行事务管理的代码块独立为方法等等。

声明式事务管理也有两种常用的方式，一种是基于tx和aop名字空间的xml配置文件，另一种就是基于@Transactional注解。显然基于注解的方式更简单易用，更清爽。

用法

@Transactional 可以作用于接口、接口方法、类以及类方法上。当作用于类上时，该类的所有 public 方法将都具有该类型的事务属性，同时，我们也可以在方法级别使用该标注来覆盖类级别的定义。

虽然 @Transactional 注解可以作用于接口、接口方法、类以及类方法上，但是 Spring 建议不要在接口或者接口方法上使用该注解，因为这只有在使用基于接口的代理时它才会生效。另外， @Transactional 注解应该只被应用到 public 方法上，这是由 Spring AOP 的本质决定的。如果你在 protected、private 或者默认可见性的方法上使用 @Transactional 注解，这将被忽略，也不会抛出任何异常。

默认情况下，只有来自外部的方法调用才会被AOP代理捕获，也就是，类内部方法调用本类内部的其他方法并不会引起事务行为，即使被调用方法使用@Transactional注解进行修饰。

```
  @Transactional(readOnly = true)



 public class DefaultFooService implements FooService {

	public Foo getFoo(String fooName) {
        // do something
     }

	// these settings have precedence for this method

 	//方法上注解属性会覆盖类注解上的相同属性
     @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
     public void updateFoo(Foo foo) {
       // do something
     }
   }
```

需要明确几点：

1. 默认配置下 Spring 只会回滚运行时、未检查异常（继承自 RuntimeException 的异常）或者 Error
2. @Transactional 注解只能应用到 public 方法才有效

异常的分类:



@Transactional 默认回滚 运行时异常和error,如果想回滚所有的异常，应该加上 rollbackFor = Throwable.class

## 6.编程式事务管理区别

TransactionTemplate 和 PlatformTransactionManager 联系、区别

```
public class TransactionTemplate extends DefaultTransactionDefinition
        implements TransactionOperations, InitializingBean {
    public <T> T execute(TransactionCallback<T> action) throws TransactionException {

        if (this.transactionManager instanceof CallbackPreferringPlatformTransactionManager) {
            return ((CallbackPreferringPlatformTransactionManager) this.transactionManager).execute(this, action);
        }
        else {
            TransactionStatus status = this.transactionManager.getTransaction(this);

            T result;
            try {
                result = action.doInTransaction(status);
            }
            catch (RuntimeException ex) {
                // Transactional code threw application exception -> rollback

                rollbackOnException(status, ex);
                throw ex;
            }
            catch (Error err) {
                // Transactional code threw error -> rollback
                rollbackOnException(status, err);
                throw err;
            }
            catch (Exception ex) {
                // Transactional code threw unexpected exception -> rollback

                rollbackOnException(status, ex);
                throw new UndeclaredThrowableException(ex, "TransactionCallback threw undeclared checked exception");
            }
            this.transactionManager.commit(status);
            return result;
        }
    }
```

由上面的代码可以推测到, 真正执行业务方法的关键代码是: action.doInTransaction(status);

正好, 有个入参TransactionCallback, 翻看该接口的源码:

```
  public interface TransactionCallback<T> {


      T doInTransaction(TransactionStatus status);


  }
```

该接口只有一个doInTransaction方法, 那么很简单, 我们可以通过匿名内部类的方式将业务代码放在doInTransaction中: 例子:

```
private PayOrderDAO payOrderDAO;  
protected TransactionTemplate transactionTemplate;  
/** 
 * 保存支付订单 
 */  
protected PayOrder savePayReq(final PayOrder payOrder) {  

    @Autowired
    private TransactionTemplate transactionTemplate;

     @Autowired
    private PayOrderDAO payOrderDAO;

    PayOrder order = (PayOrder) this.transactionTemplate.execute(new TransactionCallback() {  

                @Override  
                public Object doInTransaction(TransactionStatus status) {  

                    // 查看是否已经存在支付订单，如果已经存在则返回订单主键  

                    PayOrder payOrderTemp = payOrderDAO.findOrder(String  
.valueOf(payOrder.getPayOrderId()));  

                    // 由支付渠道类型(PayChannelType)转换得到交易类型(PayType)  
                    if (payOrder.getPayChannelId().equalsIgnoreCase(PAY_CHNL_ACT_BAL)) {// 账户余额支付  

                        payOrder.setPayType("3");  
                    } else if (payOrder.getPayChannelId().equalsIgnoreCase(PAY_CHNL_FAST_PAY)) {// 联通快捷支付  

                        payOrder.setPayType("4");  
                    } else {// 网银网关支付  
                        payOrder.setPayType("2"); 

                    }  

                    // 比对新的支付金额与原订单金额是否一致，如不一致则提示错误  
                    if (payOrderTemp == null) {  
                        String orderId = payOrderDAO.save(payOrder);  
                        payOrder.setPayOrderId(orderId);  
                        return payOrder;  
                    } else {  
                        return payOrderTemp;  
                    }  
                }  
            });  
    if ("2".equals(order.getOrderState())) {// 2：表示支付成功  
        throw new EpaymentBizException(StatusCode.DQSystem.PAY_FAIL,  
                "同一订单不能重复支付");  
    } else if (payOrder.getPayAmt().longValue() != order.getPayAmt()  
            .longValue()) {  
        throw new EpaymentBizException(StatusCode.DQSystem.PAY_FAIL,  
                "交易金额与原订单不一致");  
    } else {  
        return payOrder;  
    }  

}
```