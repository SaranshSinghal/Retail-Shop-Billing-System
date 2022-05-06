package entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString
public class Bill {

	int billID;
	LocalDateTime timestamp;
	double totalAmount;

}
// pid
// quant
