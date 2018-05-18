/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MiasTestModule } from '../../../test.module';
import { ProcedureOrderComponent } from '../../../../../../main/webapp/app/entities/procedure-order/procedure-order.component';
import { ProcedureOrderService } from '../../../../../../main/webapp/app/entities/procedure-order/procedure-order.service';
import { ProcedureOrder } from '../../../../../../main/webapp/app/entities/procedure-order/procedure-order.model';

describe('Component Tests', () => {

    describe('ProcedureOrder Management Component', () => {
        let comp: ProcedureOrderComponent;
        let fixture: ComponentFixture<ProcedureOrderComponent>;
        let service: ProcedureOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MiasTestModule],
                declarations: [ProcedureOrderComponent],
                providers: [
                    ProcedureOrderService
                ]
            })
            .overrideTemplate(ProcedureOrderComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProcedureOrderComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProcedureOrderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ProcedureOrder(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.procedureOrders[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
