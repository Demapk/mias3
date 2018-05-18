/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MiasTestModule } from '../../../test.module';
import { ProcedureOrderDetailComponent } from '../../../../../../main/webapp/app/entities/procedure-order/procedure-order-detail.component';
import { ProcedureOrderService } from '../../../../../../main/webapp/app/entities/procedure-order/procedure-order.service';
import { ProcedureOrder } from '../../../../../../main/webapp/app/entities/procedure-order/procedure-order.model';

describe('Component Tests', () => {

    describe('ProcedureOrder Management Detail Component', () => {
        let comp: ProcedureOrderDetailComponent;
        let fixture: ComponentFixture<ProcedureOrderDetailComponent>;
        let service: ProcedureOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MiasTestModule],
                declarations: [ProcedureOrderDetailComponent],
                providers: [
                    ProcedureOrderService
                ]
            })
            .overrideTemplate(ProcedureOrderDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProcedureOrderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProcedureOrderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ProcedureOrder(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.procedureOrder).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
